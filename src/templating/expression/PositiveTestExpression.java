package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.Test;

import java.util.Map;
import javax.management.AttributeNotFoundException;
import templating.node.ArgumentsNode;
import templating.node.TestInvocationExpression;

public class PositiveTestExpression extends BinaryExpression<Object> {

    private Test cachedTest;

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {

        TestInvocationExpression testInvocation = (TestInvocationExpression) getRightExpression();
        ArgumentsNode args = testInvocation.getArgs();

        if (cachedTest == null) {
            String testName = testInvocation.getTestName();

            cachedTest = context.getExtensionRegistry().getTest(testInvocation.getTestName());

            if (cachedTest == null) {
                String msg = String.format("Test [%s] does not exist at line %s  in file %s.", testName, this.getLineNumber(), self.getName());
                throw new Exception(msg);
            }
        }
        Test test = cachedTest;

        Map<String, Object> namedArguments = args.getArgumentMap(self, context, test);

        Object input;
        try {
            input = getLeftExpression().evaluate(self, context);
        } catch (AttributeNotFoundException e) {
            input = null;
        }
        
        return test.apply(input, namedArguments);

    }
}
