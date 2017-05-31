package expressionLanguage.model.tree;

import expressionLanguage.model.position.Position;
import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.IOException;
import java.io.Writer;

public class FlushNode extends Node {

    public FlushNode(Position position) {
        super(position);
    }

//    @Override
//    public void render(Template self, Writer writer, EvaluationContext context) throws IOException {
//        writer.flush();
//    }
//
//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }
}
