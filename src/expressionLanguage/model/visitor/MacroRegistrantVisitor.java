package expressionLanguage.model.visitor;

import expressionLanguage.function.Function;
import expressionLanguage.model.template.Template;
import expressionLanguage.model.tree.MacroNode;
import expressionLanguage.model.tree.Node;

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
