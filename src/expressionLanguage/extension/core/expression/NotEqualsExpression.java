package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.BinaryExpression;
import templating.operator.OperatorUtils;

public class NotEqualsExpression extends BinaryExpression<Boolean> {

    @Override
    public Boolean evaluate(EvaluationContext context) throws Exception {
        return !OperatorUtils.equals(getLeftExpression().evaluate(context), getRightExpression().evaluate(context));
    }
}
