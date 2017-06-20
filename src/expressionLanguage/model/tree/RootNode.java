package expressionLanguage.model.tree;

import expressionLanguage.model.position.Position;
import expressionLanguage.model.visitor.NodeVisitor;

public class RootNode extends Node {

    private final BodyNode body;

    public RootNode(Position position, BodyNode body) {
        super(position);
        this.body = body;
    }

//    @Override
//    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
//        body.setOnlyRenderInheritanceSafeNodes(true);
//        body.render(self, writer, context);
//    }
//
    @Override
    public void accept(NodeVisitor visitor) {
        super.accept(visitor);
        
        if (getBody() != null) {
            getBody().accept(visitor);
        }
    }

    public BodyNode getBody() {
        return body;
    }
}
