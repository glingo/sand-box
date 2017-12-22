package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.BinaryExpression;
import component.expressionLanguage.operator.OperatorUtils;

public class ModulusExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(EvaluationContext context) {
        return OperatorUtils.mod(getLeftExpression().evaluate(context),
                getRightExpression().evaluate(context));
    }
}
