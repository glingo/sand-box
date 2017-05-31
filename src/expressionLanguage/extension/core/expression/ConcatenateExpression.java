package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.BinaryExpression;

/**
 * Expression which implements the string concatenation.
 *
 */
public class ConcatenateExpression extends BinaryExpression<Object> {

    @Override
    public Object evaluate(EvaluationContext context) throws Exception {

        Object left = getLeftExpression().evaluate(context);
        Object right = getRightExpression().evaluate(context);
        
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