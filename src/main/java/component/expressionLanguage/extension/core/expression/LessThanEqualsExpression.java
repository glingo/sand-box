package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.BinaryExpression;
import component.expressionLanguage.operator.OperatorUtils;

public class LessThanEqualsExpression extends BinaryExpression<Boolean> {

    @Override
    public Boolean evaluate(EvaluationContext context) {
        return OperatorUtils.lte(getLeftExpression().evaluate(context),
                getRightExpression().evaluate(context));
    }
}
