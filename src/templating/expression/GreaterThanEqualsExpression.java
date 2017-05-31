package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.operator.OperatorUtils;

public class GreaterThanEqualsExpression extends BinaryExpression<Boolean> {

    @Override
    public Boolean evaluate(Template self, EvaluationContext context) throws Exception {
        return OperatorUtils.gte(getLeftExpression().evaluate(self, context),
                getRightExpression().evaluate(self, context));
    }
}
