package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.BinaryExpression;
import component.expressionLanguage.operator.OperatorUtils;

public class DivideExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(EvaluationContext context) {
        return OperatorUtils.divide(getLeftExpression().evaluate(context),
                getRightExpression().evaluate(context));
    }
}
