package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.BinaryExpression;
import expressionLanguage.model.tree.ArgumentsNode;
import expressionLanguage.model.tree.TestInvocationExpression;
import expressionLanguage.test.Test;

import java.util.Map;
import javax.management.AttributeNotFoundException;

public class PositiveTestExpression extends BinaryExpression<Object> {

    private Test cachedTest;

    @Override
    public Object evaluate(EvaluationContext context) throws Exception {

        TestInvocationExpression testInvocation = (TestInvocationExpression) getRightExpression();
        ArgumentsNode args = testInvocation.getArgs();

        if (cachedTest == null) {
            String testName = testInvocation.getTestName();

            cachedTest = context.getExtensionRegistry().getTest(testInvocation.getTestName());

            if (cachedTest == null) {
                String msg = String.format("Test [%s] does not exist at line %s  in file %s.", testName);
                throw new Exception(msg);
            }
        }
        
        Test test = cachedTest;

        Map<String, Object> namedArguments = args.getArgumentMap(context, test);

        Object input;
        try {
            input = getLeftExpression().evaluate(context);
        } catch (AttributeNotFoundException e) {
            input = null;
        }
        
        return test.apply(input, namedArguments);

    }
}
