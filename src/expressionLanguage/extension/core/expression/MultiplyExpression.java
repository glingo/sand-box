package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.BinaryExpression;
import templating.operator.OperatorUtils;

public class MultiplyExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(EvaluationContext context) throws Exception {
        return OperatorUtils.multiply(getLeftExpression().evaluate(context),
                getRightExpression().evaluate(context));
    }
}
