package templating.node;

import java.io.Writer;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;

public class AutoEscapeNode extends AbstractRenderableNode {

    private final BodyNode body;

    private final String strategy;

    private final boolean active;

    public AutoEscapeNode(int lineNumber, BodyNode body, boolean active, String strategy) {
        super(lineNumber);
        this.body = body;
        this.strategy = strategy;
        this.active = active;
    }

    @Override
    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
        body.render(self, writer, context);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public BodyNode getBody() {
        return body;
    }

    public String getStrategy() {
        return strategy;
    }

    public boolean isActive() {
        return active;
    }

}
