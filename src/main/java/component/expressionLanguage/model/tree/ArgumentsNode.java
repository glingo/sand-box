package component.expressionLanguage.model.tree;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.function.Function;
import component.expressionLanguage.model.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentsNode extends Node {

    private final List<ArgumentNode> args;

    public ArgumentsNode(Position position, List<ArgumentNode> args) {
        super(position);
        this.args = args;
    }

    public List<ArgumentNode> getArgs() {
        return args;
    }

    /**
     * Using hints from the filter/function/test/macro it will convert an
     * ArgumentMap (which holds both positional and named arguments) into a
     * regular Map that the filter/function/test/macro is expecting.
     *
     * @param context
     *            The evaluation context
     * @param invocableWithNamedArguments
     *            The named arguments object
     * @return Returns a map representaion of the arguments
     */
    public Map<String, Object> getArgumentMap(EvaluationContext context, Function invocableWithNamedArguments) {
        Map<String, Object> result = new HashMap<>();
        List<String> argumentNames = invocableWithNamedArguments.getArgumentNames();

        if (argumentNames == null) {

            /* Some functions such as min and max use un-named varags */
            if (args != null && !args.isEmpty()) {
                for (int i = 0; i < args.size(); i++) {
                    result.put(String.valueOf(i), args.get(i).getValue().evaluate(context));
                }
            }
        } else {

            if (args != null) {
                int nameIndex = 0;

                for (ArgumentNode arg : args) {
                    if (argumentNames.size() <= nameIndex) {
                        String msg = String.format("The argument at position %s is not allowed. Only %s argument(s) are allowed at line %s in file %s.", nameIndex + 1, argumentNames.size());
                        throw new IllegalArgumentException(msg);
                    }

                    result.put(argumentNames.get(nameIndex), arg.getValue().evaluate(context));
                    nameIndex++;
                }
            }

            if (args != null) {
                for (ArgumentNode arg : args) {
                    // check if user used an incorrect name
                    if (!argumentNames.contains(arg.getName())) {
                        String msg = String.format("The following named argument does not exist: %s at line %s in file %s", arg.getName());
                        throw new IllegalArgumentException(msg);
                    }
                    Object value = arg.getValue() == null ? null : arg.getValue().evaluate(context);
                    result.put(arg.getName(), value);
                }
            }
        }

        result.put("_context", context);

        return result;
    }

}
