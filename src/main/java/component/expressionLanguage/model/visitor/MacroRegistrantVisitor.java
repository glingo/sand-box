package component.expressionLanguage.model.visitor;

import component.expressionLanguage.function.Function;
import component.expressionLanguage.model.template.Template;
import component.expressionLanguage.model.tree.MacroNode;
import component.expressionLanguage.model.tree.Node;

public class MacroRegistrantVisitor implements NodeVisitor {
        
    private Template template;
    
    public MacroRegistrantVisitor(Template template) {
        this.template = template;
    }

    @Override
    public void visit(Node node) {
        if (!(node instanceof MacroNode)) {
            return;
        }
        Function.builder().build();
        this.template.registerMacro(((MacroNode) node).getMacro());
    }
}
