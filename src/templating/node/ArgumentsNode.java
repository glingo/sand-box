package templating.node;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NamedArguments;
import templating.extension.NodeVisitor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentsNode implements Node {

    private final List<NamedArgumentNode> namedArgs;

    private final List<PositionalArgumentNode> positionalArgs;

    private final int lineNumber;

    public ArgumentsNode(List<PositionalArgumentNode> positionalArgs, List<NamedArgumentNode> namedArgs, int lineNumber) {
        this.positionalArgs = positionalArgs;
        this.namedArgs = namedArgs;
        this.lineNumber = lineNumber;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
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
     * @param self
     *            The template implementation
     * @param context
     *            The evaluation context
     * @param invocableWithNamedArguments
     *            The named arguments object
     * @return Returns a map representaion of the arguments
     * @throws Exception Thrown if an expected name argument does not exist
     */
    public Map<String, Object> getArgumentMap(Template self, EvaluationContext context, NamedArguments invocableWithNamedArguments) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<String> argumentNames = invocableWithNamedArguments.getArgumentNames();

        if (argumentNames == null) {

            /* Some functions such as min and max use un-named varags */
            if (positionalArgs != null && !positionalArgs.isEmpty()) {
                for (int i = 0; i < positionalArgs.size(); i++) {
                    result.put(String.valueOf(i), positionalArgs.get(i).getValueExpression().evaluate(self, context));
                }
            }
        } else {

            if (positionalArgs != null) {
                int nameIndex = 0;

                for (PositionalArgumentNode arg : positionalArgs) {
                    if (argumentNames.size() <= nameIndex) {
                        String msg = String.format("The argument at position %s is not allowed. Only %s argument(s) are allowed at line %s in file %s.", nameIndex + 1, argumentNames.size(), this.lineNumber, self.getName());
                        throw new Exception(msg);
                    }

                    result.put(argumentNames.get(nameIndex), arg.getValueExpression().evaluate(self, context));
                    nameIndex++;
                }
            }

            if (namedArgs != null) {
                for (NamedArgumentNode arg : namedArgs) {
                    // check if user used an incorrect name
                    if (!argumentNames.contains(arg.getName())) {
                        String msg = String.format("The following named argument does not exist: %s at line %s in file %s", arg.getName(), this.lineNumber, self.getName());
                        throw new Exception(msg);
                    }
                    Object value = arg.getValueExpression() == null ? null : arg.getValueExpression().evaluate(self, context);
                    result.put(arg.getName(), value);
                }
            }
        }

        result.put("_self", self);
        result.put("_context", context);

        return result;
    }

}
