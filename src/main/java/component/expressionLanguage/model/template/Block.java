package component.expressionLanguage.model.template;

import component.expressionLanguage.EvaluationContext;

public interface Block {

    String getName();

    void evaluate(Template self, EvaluationContext context);
}
