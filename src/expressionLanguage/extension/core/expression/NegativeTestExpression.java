package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;

public class NegativeTestExpression extends PositiveTestExpression {

    @Override
    public Object evaluate(EvaluationContext context) {
        return !((Boolean) super.evaluate(context));
    }
}
