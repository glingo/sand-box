package expressionLanguage.model.tree;

import expressionLanguage.model.position.Position;
import expressionLanguage.model.tree.visitor.NodeVisitor;

public abstract class Node {

    private final Position position;

    protected Node(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void visit (NodeVisitor nodeVisitor) {
        nodeVisitor.accept(this);
    }
}
