package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayExpression implements Expression<List<?>> {

    private final List<Expression<?>> values;

    public ArrayExpression() {
        this.values = Collections.emptyList();
    }

    public ArrayExpression(List<Expression<?>> values) {
        if (values == null) {
            this.values = Collections.emptyList();
        } else {
            this.values = values;
        }
    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    @Override
    public List<?> evaluate(EvaluationContext context) throws Exception {
        List<Object> returnValues = new ArrayList<>(values.size());
        for (Expression<?> expr : values) {
            Object value = expr == null ? null : expr.evaluate(context);
            returnValues.add(value);
        }
        return returnValues;
    }

}
