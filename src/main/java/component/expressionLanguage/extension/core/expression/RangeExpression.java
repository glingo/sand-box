package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.BinaryExpression;
import component.expressionLanguage.function.RangeFunction;
import component.expressionLanguage.model.position.Position;
import component.expressionLanguage.model.tree.ArgumentsNode;
import component.expressionLanguage.model.tree.ArgumentNode;
import java.util.ArrayList;
import java.util.List;

/**
 * Expression which implements the range function.
 *
 */
public class RangeExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(EvaluationContext context) {
        List<ArgumentNode> positionalArgs = new ArrayList<>();
        positionalArgs.add(new ArgumentNode(null, getLeftExpression()));
        positionalArgs.add(new ArgumentNode(null, getRightExpression()));
//
        ArgumentsNode arguments = new ArgumentsNode(null, positionalArgs);
        FunctionOrMacroInvocationExpression function = new FunctionOrMacroInvocationExpression(
                RangeFunction.FUNCTION_NAME, arguments);

        return function.evaluate(context);
    }

}