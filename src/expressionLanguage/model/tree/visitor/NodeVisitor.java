package expressionLanguage.model.tree.visitor;

import expressionLanguage.model.tree.Node;
import java.util.function.Consumer;

public interface NodeVisitor extends Consumer<Node> {
    
    void accept(Node t);
}
