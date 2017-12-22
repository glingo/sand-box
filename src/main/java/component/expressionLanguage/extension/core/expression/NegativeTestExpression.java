package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;

public class NegativeTestExpression extends PositiveTestExpression {

    @Override
    public Object evaluate(EvaluationContext context) {
        return !((Boolean) super.evaluate(context));
    }
}
