package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.UnaryExpression;
import expressionLanguage.operator.OperatorUtils;

public class UnaryPlusExpression extends UnaryExpression {

    @Override
    public Object evaluate(EvaluationContext context) {
        return OperatorUtils.unaryPlus(getChildExpression().evaluate(context));
    }

}
