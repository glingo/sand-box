package templating.node;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.Writer;
import templating.expression.Expression;

public class ExtendsNode extends AbstractRenderableNode {

    Expression<?> parentExpression;

    public ExtendsNode(int lineNumber, Expression<?> parentExpression) {
        super(lineNumber);
        this.parentExpression = parentExpression;
    }

    @Override
    public void render(final Template self, Writer writer, final EvaluationContext context) throws Exception {
        self.setParent(context, (String) parentExpression.evaluate(self, context));
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Expression<?> getParentExpression() {
        return parentExpression;
    }
}
