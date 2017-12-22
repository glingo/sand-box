package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.Expression;

public class LiteralBooleanExpression implements Expression<Boolean> {

    private final Boolean value;

    public LiteralBooleanExpression(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean evaluate(EvaluationContext context) {
        return value;
    }

}
