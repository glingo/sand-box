package expressionLanguage.model.tree;

import expressionLanguage.model.position.Position;

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
