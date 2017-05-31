package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.operator.OperatorUtils;

public class EqualsExpression extends BinaryExpression<Boolean> {

    @Override
    public Boolean evaluate(Template self, EvaluationContext context) throws Exception {
        return OperatorUtils.equals(getLeftExpression().evaluate(self, context),
                getRightExpression().evaluate(self, context));
    }
}
