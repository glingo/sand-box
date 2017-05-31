package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.operator.OperatorUtils;

public class ModulusExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {
        return OperatorUtils.mod(getLeftExpression().evaluate(self, context),
                getRightExpression().evaluate(self, context));
    }
}
