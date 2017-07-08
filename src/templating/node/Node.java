package templating.node;

public interface Node {
    
    void accept(NodeVisitor visitor);
}
