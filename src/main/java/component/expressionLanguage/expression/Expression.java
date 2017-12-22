package component.expressionLanguage.expression;

import component.expressionLanguage.EvaluationContext;

@FunctionalInterface
public interface Expression<T> {

    T evaluate(EvaluationContext context);
}
