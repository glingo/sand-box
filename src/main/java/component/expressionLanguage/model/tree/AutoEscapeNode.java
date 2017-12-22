package component.expressionLanguage.model.tree;

import component.expressionLanguage.model.position.Position;

public class AutoEscapeNode extends Node {

    private final BodyNode body;

    private final String strategy;

    private final boolean active;

    public AutoEscapeNode(Position position, BodyNode body, boolean active, String strategy) {
        super(position);
        this.body = body;
        this.strategy = strategy;
        this.active = active;
    }

//    @Override
//    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
//        body.render(self, writer, context);
//    }

//    @Override
//    public void visit(NodeVisitor visitor) {
//        visitor.accept(this);
//    }

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
