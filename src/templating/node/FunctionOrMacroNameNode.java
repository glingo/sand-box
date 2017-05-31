package templating.node;

import templating.EvaluationContext;
import templating.expression.Expression;
import templating.template.Template;
import templating.extension.NodeVisitor;

public class FunctionOrMacroNameNode implements Expression<String> {

    private final String name;

    private final int lineNumber;

    public FunctionOrMacroNameNode(String name, int lineNumber) {
        this.name = name;
        this.lineNumber = lineNumber;
    }

    @Override
    public String evaluate(Template self, EvaluationContext context) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

}
