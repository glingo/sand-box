package expressionLanguage.expression;

import expressionLanguage.EvaluationContext;

public interface Expression<T> {

    T evaluate(EvaluationContext context) throws Exception;
}
