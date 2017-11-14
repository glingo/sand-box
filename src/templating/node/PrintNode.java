package templating.node;

import templating.expression.Expression;

public class PrintNode extends ExpressionNode {
    
    public PrintNode(Expression expression) {
        super(expression);
    }

    @Override
    public void accept(NodeVisitor visitor) {
    }
}
