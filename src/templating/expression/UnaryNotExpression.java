package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;

public class UnaryNotExpression extends UnaryExpression {

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {
        Boolean result = (Boolean) getChildExpression().evaluate(self, context);
        return !result;
    }

}
