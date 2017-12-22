package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.UnaryExpression;
import component.expressionLanguage.model.tree.Node;

/**
 * This class wraps a {@link RenderableNode} into an expression. This is used by
 * the filter TAG to apply a filter to large chunk of template which is
 * contained within a renderable node.
 *
 */
public class RenderableNodeExpression extends UnaryExpression {

    private final Node node;

    public RenderableNodeExpression(Node node) {
        this.node = node;
    }

    @Override
    public Object evaluate(EvaluationContext context) {
//        Writer writer = new StringWriter();
//        try {
//            node.render(self, writer, context);
//        } catch (IOException e) {
//            String msg = String.format("Error occurred while rendering node at line %s in file %s.", this.getLineNumber(), self.getName());
//            throw new Exception(msg);
//        }
//        return writer.toString();
        return node;
    }

}
