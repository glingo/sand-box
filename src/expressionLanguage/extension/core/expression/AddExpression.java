package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.BinaryExpression;
import expressionLanguage.operator.OperatorUtils;

public class AddExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(EvaluationContext context) {
        return OperatorUtils.add(getLeftExpression().evaluate(context), getRightExpression().evaluate(context));
    }
}
