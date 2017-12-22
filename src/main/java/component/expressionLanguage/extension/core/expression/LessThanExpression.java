package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.BinaryExpression;
import component.expressionLanguage.operator.OperatorUtils;

public class LessThanExpression extends BinaryExpression<Boolean> {

    @Override
    public Boolean evaluate(EvaluationContext context) {
        return OperatorUtils.lt(getLeftExpression().evaluate(context),
                getRightExpression().evaluate(context));
    }
}
