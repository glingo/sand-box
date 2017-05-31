package templating.node;

import templating.EvaluationContext;
import templating.scope.ScopeChain;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.*;
import templating.expression.Expression;

/**
 * Represents a "for" loop within the template.
 *
 */
public class ForNode extends AbstractRenderableNode {

    private final String variableName;

    private final Expression<?> iterableExpression;

    private final BodyNode body;

    private final BodyNode elseBody;

    public ForNode(int lineNumber, String variableName, Expression<?> iterableExpression, BodyNode body, BodyNode elseBody) {
        super(lineNumber);
        this.variableName = variableName;
        this.iterableExpression = iterableExpression;
        this.body = body;
        this.elseBody = elseBody;
    }

    @Override
    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
        Object iterableEvaluation = iterableExpression.evaluate(self, context);
        Iterable<?> iterable;

        if (iterableEvaluation == null) {
            return;
        }

        iterable = toIterable(iterableEvaluation);

        if (iterable == null) {
            String msg = String.format("Not an iterable object. Value = [%s] at line %s in file %s.", iterableEvaluation, getLineNumber(), self.getName());
            throw new Exception(msg);
        }

        Iterator<?> iterator = iterable.iterator();

        boolean newScope = false;

        if (iterator.hasNext()) {

            ScopeChain scopeChain = context.getScopeChain();

            /*
             * Only if there is a variable name conflict between one of the
             * variables added by the for loop construct and an existing
             * variable do we push another scope, otherwise we reuse the current
             * scope for performance purposes.
             */
            if (scopeChain.currentScopeContainsVariable("loop") || scopeChain
                    .currentScopeContainsVariable(variableName)) {
                scopeChain.pushScope();
                newScope = true;
            }

            int length = getIteratorSize(iterableEvaluation);
            int index = 0;

            Map<String, Object> loop = new HashMap<>();

            boolean usingExecutorService = context.getExecutorService() != null;

            while (iterator.hasNext()) {

                /*
                 * If the user is using an executor service (i.e. parallel node), we
                 * must create a new map with every iteration instead of
                 * re-using the same one; it's imperative that each thread would
                 * get it's own distinct copy of the context.
                 */
                if (index == 0 || usingExecutorService) {
                    loop.put("first", index == 0);
                    loop.put("last", index == length - 1);
                    loop.put("length", length);
                }else{

                    // second iteration
                    if(index == 1){
                        loop.put("first", false);
                    }

                    // last iteration
                    if(index == length - 1){
                        loop.put("last", true);
                    }
                }

                loop.put("revindex", length - index - 1);
                loop.put("index", index++);

                scopeChain.put("loop", loop);

                scopeChain.put(variableName, iterator.next());
                body.render(self, writer, context);
            }

            if (newScope) {
                scopeChain.popScope();
            }

        } else if (elseBody != null) {
            elseBody.render(self, writer, context);
        }

    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public String getIterationVariable() {
        return variableName;
    }

    public Expression<?> getIterable() {
        return iterableExpression;
    }

    public BodyNode getBody() {
        return body;
    }

    public BodyNode getElseBody() {
        return elseBody;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Iterable<Object> toIterable(final Object obj) {

        Iterable<Object> result = null;

        if (obj instanceof Iterable<?>) {

            result = (Iterable<Object>) obj;

        } else if (obj instanceof Map) {

            // raw type
            result = ((Map) obj).entrySet();

        } else if (obj.getClass().isArray()) {

            if (Array.getLength(obj) == 0) {
                return new ArrayList<>(0);
            }

            result = () -> new Iterator<Object>() {
                
                private int index = 0;
                
                private final int length = Array.getLength(obj);
                
                @Override
                public boolean hasNext() {
                    return index < length;
                }
                
                @Override
                public Object next() {
                    return Array.get(obj, index++);
                }
                
                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        return result;
    }

    private int getIteratorSize(Object iterable) {
        if (iterable == null) {
            return 0;
        }
        if (iterable instanceof Collection) {
            return ((Collection<?>) iterable).size();
        } else if (iterable instanceof Map) {
            return ((Map<?, ?>) iterable).size();
        } else if (iterable.getClass().isArray()) {
            return Array.getLength(iterable);
        }

        // assumed to be of type Iterator
        Iterator<?> it = ((Iterable<?>) iterable).iterator();
        int size = 0;
        while (it.hasNext()) {
            size++;
            it.next();
        }
        return size;
    }
}
