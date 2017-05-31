package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.core.RangeFunction;
import java.util.ArrayList;
import java.util.List;
import templating.node.ArgumentsNode;
import templating.node.PositionalArgumentNode;

/**
 * Expression which implements the range function.
 *
 */
public class RangeExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {
        List<PositionalArgumentNode> positionalArgs = new ArrayList<>();
        positionalArgs.add(new PositionalArgumentNode(getLeftExpression()));
        positionalArgs.add(new PositionalArgumentNode(getRightExpression()));

        ArgumentsNode arguments = new ArgumentsNode(positionalArgs, null, this.getLineNumber());
        FunctionOrMacroInvocationExpression function = new FunctionOrMacroInvocationExpression(
                RangeFunction.FUNCTION_NAME, arguments, this.getLineNumber());

        return function.evaluate(self, context);
    }

}