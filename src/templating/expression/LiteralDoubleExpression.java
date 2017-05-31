package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;

public class LiteralDoubleExpression implements Expression<Double> {

    private final Double value;

    private final int lineNumber;

    public LiteralDoubleExpression(Double value, int lineNumber) {
        this.value = value;
        this.lineNumber = lineNumber;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Double evaluate(Template self, EvaluationContext context) throws Exception {
        return value;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

}
