package expressionLanguage.model.visitor;

import expressionLanguage.model.tree.Node;

@FunctionalInterface
public interface NodeVisitor {
    
    void visit(Node node);
}
