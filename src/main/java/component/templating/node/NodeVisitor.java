package component.templating.node;

@FunctionalInterface
public interface NodeVisitor {
    
    void visit(Node node);
}
