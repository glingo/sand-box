package templating.node;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.IOException;
import java.io.Writer;

public class FlushNode extends AbstractRenderableNode {

    public FlushNode(int lineNumber) {
        super(lineNumber);
    }

    @Override
    public void render(Template self, Writer writer, EvaluationContext context) throws IOException {
        writer.flush();
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}
