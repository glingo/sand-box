package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.operator.OperatorUtils;

public class SubtractExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {
        return OperatorUtils.subtract(getLeftExpression().evaluate(self, context),
                getRightExpression().evaluate(self, context));
    }
}
