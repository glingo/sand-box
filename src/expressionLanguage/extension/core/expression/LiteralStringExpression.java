package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;

public class LiteralStringExpression implements Expression<String> {

    private final String value;

    public LiteralStringExpression(String value) {
        this.value = value;
    }

    @Override
    public String evaluate(EvaluationContext context) {
        return value;
    }

}
