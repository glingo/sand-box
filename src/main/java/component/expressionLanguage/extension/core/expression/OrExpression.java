package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.BinaryExpression;
import component.expressionLanguage.expression.Expression;

public class OrExpression extends BinaryExpression<Boolean> {

    @Override
    public Boolean evaluate(EvaluationContext context) {
        Expression<Boolean> left = (Expression<Boolean>) getLeftExpression();
        Expression<Boolean> right = (Expression<Boolean>) getRightExpression();
        return left.evaluate(context) || right.evaluate(context);
    }
}
