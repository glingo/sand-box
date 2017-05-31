package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;

public class ParentFunctionExpression implements Expression<String> {

    private final String blockName;

    public ParentFunctionExpression(String blockName) {
        this.blockName = blockName;
    }

    @Override
    public String evaluate(EvaluationContext context) throws Exception {
        return null;
//        Writer writer = new StringWriter();
//        try {
//            Hierarchy hierarchy = context.getHierarchy();
//            if (hierarchy.getParent() == null) {
//                String msg = String.format("Can not use parent function if template does not extend another template at line %s in file %s.", lineNumber, self.getName());
//                throw new Exception(msg);
//            }
//            Template parent = hierarchy.getParent();
//
//            hierarchy.ascend();
//            parent.block(writer, context, blockName, true);
//            hierarchy.descend();
//        } catch (IOException e) {
//            String msg = String.format("Could not render block [%s] at line %s in file %s.", blockName, this.getLineNumber(), self.getName());
//            throw new Exception(msg);
//        }
//        return writer.toString();
    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

}
