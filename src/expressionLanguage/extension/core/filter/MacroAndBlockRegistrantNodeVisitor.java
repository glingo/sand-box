package expressionLanguage.extension.core.filter;

import templating.extension.core.*;
import templating.template.Template;
import templating.extension.AbstractNodeVisitor;
import templating.node.BlockNode;
import templating.node.MacroNode;

public class MacroAndBlockRegistrantNodeVisitor extends AbstractNodeVisitor {

    public MacroAndBlockRegistrantNodeVisitor(Template template) {
        super(template);
    }

    @Override
    public void visit(BlockNode node) {
        this.getTemplate().registerBlock(node.getBlock());
        super.visit(node);
    }

    @Override
    public void visit(MacroNode node) {
        try {
            this.getTemplate().registerMacro(node.getMacro());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        super.visit(node);
    }
}
