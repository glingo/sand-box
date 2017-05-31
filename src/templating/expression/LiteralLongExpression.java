package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;

public class LiteralLongExpression implements Expression<Long> {

    private final Long value;
    private final int lineNumber;

    public LiteralLongExpression(Long value, int lineNumber) {
        this.value = value;
        this.lineNumber = lineNumber;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Long evaluate(Template self, EvaluationContext context) throws Exception {
        return value;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

}
