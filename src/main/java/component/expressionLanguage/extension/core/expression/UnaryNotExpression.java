package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.UnaryExpression;

public class UnaryNotExpression extends UnaryExpression {

    @Override
    public Object evaluate(EvaluationContext context) {
        Boolean result = (Boolean) getChildExpression().evaluate(context);
        return !result;
    }

}
