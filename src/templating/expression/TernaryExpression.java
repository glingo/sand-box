package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;

public class TernaryExpression implements Expression<Object> {

    private final Expression<Boolean> expression1;

    private Expression<?> expression2;

    private Expression<?> expression3;

    private final int lineNumber;

    public TernaryExpression(Expression<Boolean> expression1, Expression<?> expression2, Expression<?> expression3, int lineNumber, String filename) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.lineNumber = lineNumber;
    }

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {
        if (expression1.evaluate(self, context) != null && expression1.evaluate(self, context)) {
            return expression2.evaluate(self, context);
        } else {
            return expression3.evaluate(self, context);
        }
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

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

    @Override
    public int getLineNumber() {
       return this.lineNumber;
    }
}
