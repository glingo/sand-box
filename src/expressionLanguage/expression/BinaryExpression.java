package expressionLanguage.expression;

public abstract class BinaryExpression<T> implements Expression<T> {

    private Expression<?> leftExpression;

    private Expression<?> rightExpression;

    public void setLeftExpression(Expression<?> left) {
        this.leftExpression = left;
    }

    public void setRightExpression(Expression<?> right) {
        this.rightExpression = right;
    }

    public Expression<?> getLeftExpression() {
        return leftExpression;
    }

    public Expression<?> getRightExpression() {
        return rightExpression;
    }
}
