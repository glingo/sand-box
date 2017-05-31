package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;

public class NegativeTestExpression extends PositiveTestExpression {

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {
        return !((Boolean) super.evaluate(self, context));
    }
}
