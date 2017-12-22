package component.expressionLanguage.model.tree;

import component.expressionLanguage.model.position.Position;

/**
 * The right hand side to the test expression.
 */
public class TestInvocationExpressionNode extends Node {

    private final String testName;

    private final ArgumentsNode args;

    public TestInvocationExpressionNode(Position position, String testName, ArgumentsNode args) {
        super(position);
        this.testName = testName;
        this.args = args;
    }

    public ArgumentsNode getArgs() {
        return args;
    }

    public String getTestName() {
        return testName;
    }

}
