package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.operator.OperatorUtils;

public class UnaryMinusExpression extends UnaryExpression {

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {
        return OperatorUtils.unaryMinus(getChildExpression().evaluate(self, context));
    }

}
