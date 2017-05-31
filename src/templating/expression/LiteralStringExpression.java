package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;

public class LiteralStringExpression implements Expression<String> {

    private final String value;

    private final int lineNumber;

    public LiteralStringExpression(String value, int lineNumber) {
        this.value = value;
        this.lineNumber = lineNumber;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String evaluate(Template self, EvaluationContext context) throws Exception {
        return value;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

}
