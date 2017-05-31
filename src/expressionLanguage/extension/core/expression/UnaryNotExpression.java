package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.UnaryExpression;

public class UnaryNotExpression extends UnaryExpression {

    @Override
    public Object evaluate(EvaluationContext context) throws Exception {
        Boolean result = (Boolean) getChildExpression().evaluate(context);
        return !result;
    }

}
