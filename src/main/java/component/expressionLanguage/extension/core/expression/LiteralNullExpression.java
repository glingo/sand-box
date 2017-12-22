package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.Expression;

public class LiteralNullExpression implements Expression<Object> {

    public LiteralNullExpression() {
    }

    @Override
    public Object evaluate(EvaluationContext context) {
        return null;
    }

}
