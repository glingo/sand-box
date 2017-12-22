package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.BinaryExpression;
import component.expressionLanguage.expression.Expression;
import component.expressionLanguage.test.Test;

public class PositiveTestExpression extends BinaryExpression<Object> {

    private Test cachedTest;

    @Override
    public Object evaluate(EvaluationContext context) {

        Expression testInvocation = getRightExpression();

        if (cachedTest == null) {
            String testName = testInvocation.getClass().getName();

            cachedTest = context.getExtensionRegistry().getTest(testName);

            if (cachedTest == null) {
                String msg = String.format("Test [%s] does not exist at line %s.", testName);
                throw new IllegalStateException(msg);
            }
        }
        
        Test test = cachedTest;

        Object input = getLeftExpression().evaluate(context);
        
        return test.apply(input);

    }
}
