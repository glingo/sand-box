package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.UnaryExpression;
import templating.operator.OperatorUtils;

public class UnaryPlusExpression extends UnaryExpression {

    @Override
    public Object evaluate(EvaluationContext context) throws Exception {
        return OperatorUtils.unaryPlus(getChildExpression().evaluate(context));
    }

}
