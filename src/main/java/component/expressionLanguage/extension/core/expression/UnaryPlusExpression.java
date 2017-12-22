package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.UnaryExpression;
import component.expressionLanguage.operator.OperatorUtils;

public class UnaryPlusExpression extends UnaryExpression {

    @Override
    public Object evaluate(EvaluationContext context) {
        return OperatorUtils.unaryPlus(getChildExpression().evaluate(context));
    }

}
