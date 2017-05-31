package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;
import expressionLanguage.model.tree.ArgumentsNode;
import templating.extension.NodeVisitor;
import java.io.StringWriter;
import java.io.Writer;

public class BlockFunctionExpression implements Expression<String> {

    private final Expression<?> blockNameExpression;

    public BlockFunctionExpression(ArgumentsNode args) {
        this.blockNameExpression = args.getPositionalArgs().get(0).getValueExpression();
    }

    @Override
    public String evaluate(EvaluationContext context) throws Exception {
//        Writer writer = new StringWriter();
        String blockName = (String) blockNameExpression.evaluate(context);
        return blockName;
//        try {
//            self.block(writer, context, blockName, false);
//        } catch (Exception e) {
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
