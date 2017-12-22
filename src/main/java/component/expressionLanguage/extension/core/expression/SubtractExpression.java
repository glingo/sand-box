package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.BinaryExpression;
import component.expressionLanguage.operator.OperatorUtils;

public class SubtractExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(EvaluationContext context) {
        return OperatorUtils.subtract(getLeftExpression().evaluate(context),
                getRightExpression().evaluate(context));
    }
}
