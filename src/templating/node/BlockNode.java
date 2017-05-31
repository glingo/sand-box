package templating.node;

import templating.template.Block;
import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.IOException;
import java.io.Writer;

public class BlockNode extends AbstractRenderableNode {

    private final BodyNode body;

    private String name;

    public BlockNode(int lineNumber, String name) {
        this(lineNumber, name, null);
    }

    public BlockNode(int lineNumber, String name, BodyNode body) {
        super(lineNumber);
        this.body = body;
        this.name = name;
    }

    @Override
    public void render(final Template self, Writer writer, EvaluationContext context) throws Exception {
        self.block(writer, context, name, false);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Block getBlock() {
        return new Block() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public void evaluate(Template self, Writer writer, EvaluationContext context) throws Exception, IOException {
                body.render(self, writer, context);
            }
        };
    }

    public BodyNode getBody() {
        return body;
    }

    public String getName() {
        return name;
    }
    
}
