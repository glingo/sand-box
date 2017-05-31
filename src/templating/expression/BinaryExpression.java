package templating.expression;

import templating.extension.NodeVisitor;

public abstract class BinaryExpression<T> implements Expression<T> {

    private int lineNumber;

    private Expression<?> leftExpression;

    private Expression<?> rightExpression;

    public BinaryExpression() {

    }

    public void setLeft(Expression<?> left) {
        this.leftExpression = left;
    }

    public void setRight(Expression<?> right) {
        this.rightExpression = right;
    }

    public Expression<?> getLeftExpression() {
        return leftExpression;
    }

    public Expression<?> getRightExpression() {
        return rightExpression;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
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
