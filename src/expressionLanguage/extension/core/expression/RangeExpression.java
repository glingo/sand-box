package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.BinaryExpression;
import expressionLanguage.function.RangeFunction;
import expressionLanguage.model.position.Position;
import expressionLanguage.model.tree.ArgumentsNode;
import expressionLanguage.model.tree.PositionalArgumentNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Expression which implements the range function.
 *
 * @author Eric Bussieres
 *
 */
public class RangeExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(EvaluationContext context) throws Exception {
        List<PositionalArgumentNode> positionalArgs = new ArrayList<>();
//        positionalArgs.add(new PositionalArgumentNode(getLeftExpression()));
//        positionalArgs.add(new PositionalArgumentNode(getRightExpression()));
//
        ArgumentsNode arguments = new ArgumentsNode(null, positionalArgs, null);
        FunctionOrMacroInvocationExpression function = new FunctionOrMacroInvocationExpression(
                RangeFunction.FUNCTION_NAME, arguments);

        return function.evaluate(context);
    }

}