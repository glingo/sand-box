package expressionLanguage.expression;

public abstract class UnaryExpression implements Expression<Object> {

    private Expression<?> childExpression;

    public Expression<?> getChildExpression() {
        return childExpression;
    }

    public void setChildExpression(Expression<?> childExpression) {
        this.childExpression = childExpression;
    }
}
