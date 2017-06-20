package expressionLanguage.model.template;

import expressionLanguage.EvaluationContext;

public interface Block {

    String getName();

    void evaluate(Template self, EvaluationContext context);
}
