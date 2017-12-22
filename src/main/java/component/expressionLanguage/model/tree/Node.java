package component.expressionLanguage.model.tree;

import component.expressionLanguage.model.position.Position;
import component.expressionLanguage.model.visitor.NodeVisitor;

public abstract class Node {

    private final Position position;

    protected Node(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}
