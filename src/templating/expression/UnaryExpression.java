package templating.expression;

import templating.extension.NodeVisitor;


public abstract class UnaryExpression implements Expression<Object> {

    private Expression<?> childExpression;

    private int lineNumber;

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Expression<?> getChildExpression() {
        return childExpression;
    }

    public void setChildExpression(Expression<?> childExpression) {
        this.childExpression = childExpression;
    }


    /**
     * Sets the line number on which the expression is defined on.
     *
     * @param lineNumber
     *            the line number on which the expression is defined on.
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }


}
