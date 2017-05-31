package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.BinaryExpression;
import templating.operator.OperatorUtils;

public class LessThanExpression extends BinaryExpression<Boolean> {

    @Override
    public Boolean evaluate(EvaluationContext context) throws Exception {
        return OperatorUtils.lt(getLeftExpression().evaluate(context),
                getRightExpression().evaluate(context));
    }
}
