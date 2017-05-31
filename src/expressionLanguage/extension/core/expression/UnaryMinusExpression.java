package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.UnaryExpression;
import templating.operator.OperatorUtils;

public class UnaryMinusExpression extends UnaryExpression {

    @Override
    public Object evaluate(EvaluationContext context) throws Exception {
        return OperatorUtils.unaryMinus(getChildExpression().evaluate(context));
    }

}
