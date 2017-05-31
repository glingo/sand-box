package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.operator.OperatorUtils;

public class AddExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {
        return OperatorUtils.add(getLeftExpression().evaluate(self, context), getRightExpression().evaluate(self, context));
    }
}
