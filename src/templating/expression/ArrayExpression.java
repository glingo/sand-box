package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayExpression implements Expression<List<?>> {

    private final List<Expression<?>> values;
    private final int lineNumber;

    public ArrayExpression(int lineNumber) {
        this.values = Collections.emptyList();
        this.lineNumber = lineNumber;
    }

    public ArrayExpression(List<Expression<?>> values, int lineNumber) {
        if (values == null) {
            this.values = Collections.emptyList();
        } else {
            this.values = values;
        }
        this.lineNumber = lineNumber;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<?> evaluate(Template self, EvaluationContext context) throws Exception {
        List<Object> returnValues = new ArrayList<>(values.size());
        for (Expression<?> expr : values) {
            Object value = expr == null ? null : expr.evaluate(self, context);
            returnValues.add(value);
        }
        return returnValues;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

}
