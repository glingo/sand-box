package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;

public class LiteralDoubleExpression implements Expression<Double> {

    private final Double value;

    public LiteralDoubleExpression(Double value) {
        this.value = value;
    }
    
    @Override
    public Double evaluate(EvaluationContext context) {
        return value;
    }

}
