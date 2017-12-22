package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.Expression;

public class LiteralLongExpression implements Expression<Long> {

    private final Long value;

    public LiteralLongExpression(Long value) {
        this.value = value;
    }

    @Override
    public Long evaluate(EvaluationContext context) {
        return value;
    }

}
