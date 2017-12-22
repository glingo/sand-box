package component.templating.node;

import component.templating.expression.Expression;

public class ExecuteNode extends ExpressionNode {
    
    public ExecuteNode(Expression expression) {
        super(expression);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        System.out.format("Execute : %n");
        super.accept(visitor);
    }
}
