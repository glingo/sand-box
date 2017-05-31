package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.BinaryExpression;
import templating.operator.OperatorUtils;

public class GreaterThanExpression extends BinaryExpression<Boolean> {

    @Override
    public Boolean evaluate(EvaluationContext context) throws Exception {
        return OperatorUtils.gt(getLeftExpression().evaluate(context),
                getRightExpression().evaluate(context));
    }
}
