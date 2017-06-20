package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;
import expressionLanguage.model.template.Hierarchy;
import expressionLanguage.model.template.Template;

public class ParentFunctionExpression implements Expression<Boolean> {

    private final String blockName;

    public ParentFunctionExpression(String blockName) {
        this.blockName = blockName;
    }

    @Override
    public Boolean evaluate(EvaluationContext context) {
        Hierarchy hierarchy = context.getHierarchy();
        if (hierarchy.getParent() == null) {
            String msg = String.format("Can not use parent function if template does not extend another template");
            throw new IllegalStateException(msg);
        }
        
        Template parent = hierarchy.getParent();

        hierarchy.ascend();
        parent.block(context, blockName, true);
        hierarchy.descend();
        
        return true;
    }
}
