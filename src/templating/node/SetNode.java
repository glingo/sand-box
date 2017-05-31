package templating.node;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.Writer;
import templating.expression.Expression;

public class SetNode extends AbstractRenderableNode {

    private final String name;

    private final Expression<?> value;

    public SetNode(int lineNumber, String name, Expression<?> value) {
        super(lineNumber);
        this.name = name;
        this.value = value;
    }

    @Override
    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
        context.getScopeChain().put(name, value.evaluate(self, context));
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Expression<?> getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
