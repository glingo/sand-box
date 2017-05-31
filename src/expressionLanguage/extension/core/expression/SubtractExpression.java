package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.BinaryExpression;
import templating.operator.OperatorUtils;

public class SubtractExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(EvaluationContext context) throws Exception {
        return OperatorUtils.subtract(getLeftExpression().evaluate(context),
                getRightExpression().evaluate(context));
    }
}
