package expressionLanguage.model.visitor;

import expressionLanguage.model.template.Template;
import expressionLanguage.model.tree.BlockNode;
import expressionLanguage.model.tree.Node;

public class BlockRegistrantVisitor implements NodeVisitor {
        
    private Template template;
    
    public BlockRegistrantVisitor(Template template) {
        this.template = template;
    }

    @Override
    public void visit(Node node) {
        if (!(node instanceof BlockNode)) {
            return;
        }
        
        this.template.registerBlock(((BlockNode) node).getBlock());
    }

}
