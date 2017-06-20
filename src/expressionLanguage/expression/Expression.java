package expressionLanguage.expression;

import expressionLanguage.EvaluationContext;

@FunctionalInterface
public interface Expression<T> {

    T evaluate(EvaluationContext context);
}
