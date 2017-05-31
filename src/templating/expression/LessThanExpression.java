package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.operator.OperatorUtils;

public class LessThanExpression extends BinaryExpression<Boolean> {

    @Override
    public Boolean evaluate(Template self, EvaluationContext context) throws Exception {
        return OperatorUtils.lt(getLeftExpression().evaluate(self, context),
                getRightExpression().evaluate(self, context));
    }
}
