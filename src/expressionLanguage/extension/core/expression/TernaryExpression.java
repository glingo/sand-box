package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;

public class TernaryExpression implements Expression<Object> {

    private final Expression<Boolean> expression1;

    private Expression<?> expression2;

    private Expression<?> expression3;

    public TernaryExpression(Expression<Boolean> expression1, Expression<?> expression2, Expression<?> expression3, String filename) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }

    @Override
    public Object evaluate(EvaluationContext context) throws Exception {
        if (expression1.evaluate(context) != null && expression1.evaluate(context)) {
            return expression2.evaluate(context);
        } else {
            return expression3.evaluate(context);
        }
    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public Expression<Boolean> getExpression1() {
        return expression1;
    }

    public Expression<?> getExpression2() {
        return expression2;
    }

    public Expression<?> getExpression3() {
        return expression3;
    }

    public void setExpression3(Expression<?> expression3) {
        this.expression3 = expression3;
    }

    public void setExpression2(Expression<?> expression2) {
        this.expression2 = expression2;
    }
}
