package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.BinaryExpression;
import expressionLanguage.expression.Expression;

public class OrExpression extends BinaryExpression<Boolean> {

    @Override
    public Boolean evaluate(EvaluationContext context) throws Exception {
        Expression<Boolean> left = (Expression<Boolean>) getLeftExpression();
        Expression<Boolean> right = (Expression<Boolean>) getRightExpression();
        return left.evaluate(context) || right.evaluate(context);
    }
}
