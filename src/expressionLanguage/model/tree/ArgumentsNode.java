package expressionLanguage.model.tree;

import expressionLanguage.EvaluationContext;
import expressionLanguage.NamedArguments;
import expressionLanguage.model.position.Position;
import expressionLanguage.model.tree.visitor.NodeVisitor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentsNode extends Node {

    private final List<NamedArgumentNode> namedArgs;

    private final List<PositionalArgumentNode> positionalArgs;

    public ArgumentsNode(Position position, List<PositionalArgumentNode> positionalArgs, List<NamedArgumentNode> namedArgs) {
        super(position);
        this.positionalArgs = positionalArgs;
        this.namedArgs = namedArgs;
    }

    @Override
    public void visit(NodeVisitor visitor) {
        visitor.accept(this);
    }

    public List<NamedArgumentNode> getNamedArgs() {
        return namedArgs;
    }

    public List<PositionalArgumentNode> getPositionalArgs() {
        return positionalArgs;
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
     * @throws Exception Thrown if an expected name argument does not exist
     */
    public Map<String, Object> getArgumentMap(EvaluationContext context, NamedArguments invocableWithNamedArguments) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<String> argumentNames = invocableWithNamedArguments.getArgumentNames();

        if (argumentNames == null) {

            /* Some functions such as min and max use un-named varags */
            if (positionalArgs != null && !positionalArgs.isEmpty()) {
                for (int i = 0; i < positionalArgs.size(); i++) {
                    result.put(String.valueOf(i), positionalArgs.get(i).getValueExpression().evaluate(context));
                }
            }
        } else {

            if (positionalArgs != null) {
                int nameIndex = 0;

                for (PositionalArgumentNode arg : positionalArgs) {
                    if (argumentNames.size() <= nameIndex) {
                        String msg = String.format("The argument at position %s is not allowed. Only %s argument(s) are allowed at line %s in file %s.", nameIndex + 1, argumentNames.size());
                        throw new Exception(msg);
                    }

                    result.put(argumentNames.get(nameIndex), arg.getValueExpression().evaluate(context));
                    nameIndex++;
                }
            }

            if (namedArgs != null) {
                for (NamedArgumentNode arg : namedArgs) {
                    // check if user used an incorrect name
                    if (!argumentNames.contains(arg.getName())) {
                        String msg = String.format("The following named argument does not exist: %s at line %s in file %s", arg.getName());
                        throw new Exception(msg);
                    }
                    Object value = arg.getValueExpression() == null ? null : arg.getValueExpression().evaluate(context);
                    result.put(arg.getName(), value);
                }
            }
        }

        result.put("_context", context);

        return result;
    }

}
