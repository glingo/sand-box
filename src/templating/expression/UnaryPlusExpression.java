package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.operator.OperatorUtils;

public class UnaryPlusExpression extends UnaryExpression {

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {
        return OperatorUtils.unaryPlus(getChildExpression().evaluate(self, context));
    }

}
