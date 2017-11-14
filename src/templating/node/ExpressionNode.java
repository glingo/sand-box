package templating.node;

import templating.expression.Expression;

public abstract class ExpressionNode implements Node {
    
    private Expression expression;

    public ExpressionNode(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
}
