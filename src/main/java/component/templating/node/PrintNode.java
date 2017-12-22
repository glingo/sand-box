package component.templating.node;

import component.templating.expression.Expression;

public class PrintNode extends ExpressionNode {
    
    public PrintNode(Expression expression) {
        super(expression);
    }
    
    @Override
    public void accept(NodeVisitor visitor) {
        System.out.format("Print");
        super.accept(visitor);
    }
}
