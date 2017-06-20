package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;
import expressionLanguage.model.tree.ArgumentsNode;
import expressionLanguage.model.tree.ArgumentNode;
import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Used to get an attribute from an object. It will look up attributes in the
 * following order: map entry, array item, list item, get method, is method, has
 * method, public method, public field.
 *
 */
public class GetAttributeExpression implements Expression<Object> {

    private final Expression<?> node;

    private final Expression<?> attributeNameExpression;

    private final ArgumentsNode args;

    private final String filename;

    /**
     * Potentially cached on first evaluation.
     */
    private final ConcurrentHashMap<MemberCacheKey, Member> memberCache;

    public GetAttributeExpression(Expression<?> node, Expression<?> attributeNameExpression, String filename) {
        this(node, attributeNameExpression, null, filename);
    }

    public GetAttributeExpression(Expression<?> node, Expression<?> attributeNameExpression, ArgumentsNode args, String filename) {

        this.node = node;
        this.attributeNameExpression = attributeNameExpression;
        this.args = args;
        this.filename = filename;

        /*
         * I dont imagine that users will often give different types to the same
         * template so we will give this cache a pretty small initial capacity.
         */
        this.memberCache = new ConcurrentHashMap<>(2, 0.9f, 1);
    }

    @Override
    public Object evaluate(EvaluationContext context) {
        Object object = node.evaluate(context);
        Object attributeNameValue = attributeNameExpression.evaluate(context);
        String attributeName = String.valueOf(attributeNameValue);

        Object result = null;

        Object[] argumentValues = null;

        Member member = object == null ? null : memberCache.get(new MemberCacheKey(object.getClass(), attributeName));

        if (object != null && member == null) {

            /*
             * If, and only if, no arguments were provided does it make sense to
             * check maps/arrays/lists
             */
            if (args == null) {

                // first we check maps
                if (object instanceof Map && ((Map<?, ?>) object).containsKey(attributeNameValue)) {
                    return ((Map<?, ?>) object).get(attributeNameValue);
                }

                try {

                    // then we check arrays
                    if (object.getClass().isArray()) {
                        int index = Integer.parseInt(attributeName);
                        int length = Array.getLength(object);
                        if (index < 0 || index >= length) {
                            if (context.isStrictVariables()) {
                                String msg = String.format("Index out of bounds while accessing array with strict variables on, on attribute %s in file %s.", attributeName, filename);
                                throw new IndexOutOfBoundsException(msg);
                            } else {
                                return null;
                            }
                        }
                        return Array.get(object, index);
                    }

                    // then lists
                    if (object instanceof List) {

                        @SuppressWarnings("unchecked")
                        List<Object> list = (List<Object>) object;

                        int index = Integer.parseInt(attributeName);
                        int length = list.size();

                        if (index < 0 || index >= length) {
                            if (context.isStrictVariables()) {
                                String msg = String.format("Index out of bounds while accessing array with strict variables on, on attribute %s in file %s.", attributeName, filename);
                                throw new IndexOutOfBoundsException(msg);
                            } else {
                                return null;
                            }
                        }

                        return list.get(index);
                    }
                } catch (NumberFormatException ex) {
                    // do nothing
                }

            }

            /*
             * turn args into an array of types and an array of values in order
             * to use them for our reflection calls
             */
            argumentValues = getArgumentValues(context);
            Class<?>[] argumentTypes = new Class<?>[argumentValues.length];

            for (int i = 0; i < argumentValues.length; i++) {
                Object o = argumentValues[i];
                if (o == null) {
                    argumentTypes[i] = null;
                } else {
                    argumentTypes[i] = o.getClass();
                }
            }

            member = reflect(object, attributeName, argumentTypes);
            if (member != null) {
                memberCache.put(new MemberCacheKey(object.getClass(), attributeName), member);
            }

        }

        if (object != null && member != null) {
            if (argumentValues == null) {
                argumentValues = getArgumentValues(context);
            }
            result = invokeMember(object, member, argumentValues);
        } else if (context.isStrictVariables()) {
            if (object == null) {
                if (node instanceof ContextVariableExpression) {
                    final String rootPropertyName = ((ContextVariableExpression) node).getName();
                    String msg = String.format("Root attribute [%s] does not exist or can not be accessed and strict variables is set to true at line %s in file %s.", rootPropertyName, this.filename);
                    throw new IllegalStateException(msg);
                }
                
                String msg = String.format("Attempt to get attribute of null object and strict variables is set to true at line %s in file %s.", attributeName, this.filename);
                throw new NullPointerException(msg);

            }
            String msg = String.format("Attribute [%s] of [%s] does not exist or can not be accessed and strict variables is set to true at line %s in file %s.",attributeName, object.getClass().getName(), this.filename);
            throw new IllegalStateException(msg);
        }
        return result;

    }

    /**
     * Invoke the "Member" that was found via reflection.
     *
     * @param object
     * @param member
     * @param argumentValues
     * @return
     */
    private Object invokeMember(Object object, Member member, Object[] argumentValues) {
        Object result = null;
        try {
            if (member instanceof Method) {
                result = ((Method) member).invoke(object, argumentValues);
            } else if (member instanceof Field) {
                result = ((Field) member).get(object);
            }

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Fully evaluates the individual arguments.
     *
     * @param self
     * @param context
     * @return
     */
    private Object[] getArgumentValues(EvaluationContext context) {

        Object[] argumentValues;

        if (this.args == null) {
            argumentValues = new Object[0];
        } else {
            List<ArgumentNode> args = this.args.getArgs();

            argumentValues = new Object[args.size()];

            int index = 0;
            for (ArgumentNode arg : args) {
                Object argumentValue = arg.getValue().evaluate(context);
                argumentValues[index] = argumentValue;
                index++;
            }
        }
        return argumentValues;
    }

    /**
     * Performs the actual reflection to obtain a "Member" from a class.
     *
     * @param object
     * @param attributeName
     * @param parameterTypes
     * @return
     */
    private Member reflect(Object object, String attributeName, Class<?>[] parameterTypes) {

        Class<?> clazz = object.getClass();

        Member result = null;

        // capitalize first letter of attribute for the following attempts
        String attributeCapitalized = Character.toUpperCase(attributeName.charAt(0)) + attributeName.substring(1);

        // check get method
        result = findMethod(clazz, "get" + attributeCapitalized, parameterTypes);

        // check is method
        if (result == null) {
            result = findMethod(clazz, "is" + attributeCapitalized, parameterTypes);
        }

        // check has method
        if (result == null) {
            result = findMethod(clazz, "has" + attributeCapitalized, parameterTypes);
        }

        // check if attribute is a public method
        if (result == null) {
            result = findMethod(clazz, attributeName, parameterTypes);
        }

        // public field
        if (result == null) {
            try {
                result = clazz.getField(attributeName);
            } catch (NoSuchFieldException | SecurityException e) {
            }
        }

        if (result != null) {
            ((AccessibleObject) result).setAccessible(true);
        }

        return result;
    }

    /**
     * Finds an appropriate method by comparing if parameter types are
     * compatible. This is more relaxed than class.getMethod.
     *
     * @param clazz
     * @param name
     * @param requiredTypes
     * @return
     */
    private Method findMethod(Class<?> clazz, String name, Class<?>[] requiredTypes) {
        Method result = null;

        Method[] candidates = clazz.getMethods();

        for (Method candidate : candidates) {
            if (!candidate.getName().equalsIgnoreCase(name)) {
                continue;
            }

            Class<?>[] types = candidate.getParameterTypes();

            if (types.length != requiredTypes.length) {
                continue;
            }

            boolean compatibleTypes = true;
            for (int i = 0; i < types.length; i++) {
                if (requiredTypes[i] != null && !widen(types[i]).isAssignableFrom(requiredTypes[i])) {
                    compatibleTypes = false;
                    break;
                }
            }

            if (compatibleTypes) {
                result = candidate;
                break;
            }
        }
        return result;
    }

    /**
     * Performs a widening conversion (primitive to boxed type)
     *
     * @param clazz
     * @return
     */
    private Class<?> widen(Class<?> clazz) {
        Class<?> result = clazz;
        if (clazz == int.class) {
            result = Integer.class;
        } else if (clazz == long.class) {
            result = Long.class;
        } else if (clazz == double.class) {
            result = Double.class;
        } else if (clazz == float.class) {
            result = Float.class;
        } else if (clazz == short.class) {
            result = Short.class;
        } else if (clazz == byte.class) {
            result = Byte.class;
        } else if (clazz == boolean.class) {
            result = Boolean.class;
        }
        return result;
    }

    private class MemberCacheKey {
        private final Class<?> clazz;
        private final String attributeName;

        private MemberCacheKey(Class<?> clazz, String attributeName) {
            this.clazz = clazz;
            this.attributeName = attributeName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MemberCacheKey that = (MemberCacheKey) o;

            if (!clazz.equals(that.clazz)) return false;
            return attributeName.equals(that.attributeName);

        }

        @Override
        public int hashCode() {
            int result = clazz.hashCode();
            result = 31 * result + attributeName.hashCode();
            return result;
        }
    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public Expression<?> getNode() {
        return node;
    }

    public ArgumentsNode getArgumentsNode() {
        return args;
    }

}
