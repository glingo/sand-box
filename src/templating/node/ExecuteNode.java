package templating.node;

import templating.expression.Expression;

public class ExecuteNode extends ExpressionNode {
    
    public ExecuteNode(Expression expression) {
        super(expression);
    }

    @Override
    public void accept(NodeVisitor visitor) {
    }
}
