package templating.node;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.Writer;

public class RootNode extends AbstractRenderableNode {

    private final BodyNode body;

    public RootNode(BodyNode body) {
        super(0);
        this.body = body;
    }

    @Override
    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
        body.setOnlyRenderInheritanceSafeNodes(true);
        body.render(self, writer, context);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public BodyNode getBody() {
        return body;
    }
}
