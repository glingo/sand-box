package component.templating.node;

public interface Node {
    default void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}
