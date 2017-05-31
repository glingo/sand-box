package expressionLanguage.model.tree;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;

/**
 * The right hand side to the test expression.
 */
public class TestInvocationExpression extends Node implements Expression<Object> {

    private final String testName;

    private final ArgumentsNode args;

    @Override
    public Object evaluate(EvaluationContext context) throws Exception {
        throw new UnsupportedOperationException();
    }

    public TestInvocationExpression(Position position, String testName, ArgumentsNode args) {
        super(position);
        this.testName = testName;
        this.args = args;
    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public ArgumentsNode getArgs() {
        return args;
    }

    public String getTestName() {
        return testName;
    }

//    @Override
//    public int getLineNumber() {
//        return this.lineNumber;
//    }

}
