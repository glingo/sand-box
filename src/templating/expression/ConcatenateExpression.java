package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;

/**
 * Expression which implements the string concatenation.
 *
 */
public class ConcatenateExpression extends BinaryExpression<Object> {

    @Override
    public String evaluate(Template self, EvaluationContext context) throws Exception {

        Object left = getLeftExpression().evaluate(self, context);
        Object right = getRightExpression().evaluate(self, context);
        StringBuilder result = new StringBuilder();
        if (left != null) {
            result.append(left.toString());
        }
        if (right != null) {
            result.append(right.toString());
        }

        return result.toString();
    }

}
