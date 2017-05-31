package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;

public class OrExpression extends BinaryExpression<Boolean> {

    @Override
    public Boolean evaluate(Template self, EvaluationContext context) throws Exception {
        Expression<Boolean> left = (Expression<Boolean>) getLeftExpression();
        Expression<Boolean> right = (Expression<Boolean>) getRightExpression();
        return left.evaluate(self, context) || right.evaluate(self, context);
    }
}
