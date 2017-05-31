package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;

public class LiteralNullExpression implements Expression<Object> {

    private final int lineNumber;

    public LiteralNullExpression(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {
        return null;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

}
