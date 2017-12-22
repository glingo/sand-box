package component.expressionLanguage.model.visitor;

import component.expressionLanguage.model.tree.Node;

@FunctionalInterface
public interface NodeVisitor {
    
    void visit(Node node);
}
