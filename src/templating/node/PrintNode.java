package templating.node;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import templating.expression.Expression;
import java.io.Writer;
import utils.ObjectUtils;

public class PrintNode extends AbstractRenderableNode {

    private Expression<?> expression;

    public PrintNode(Expression<?> expression, int lineNumber) {
        super(lineNumber);
        this.expression = expression;
    }

    @Override
    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
        Object var = expression.evaluate(self, context);
        if (var != null) {
            writer.write(ObjectUtils.nullSafeToString(var));
        }
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Expression<?> getExpression() {
        return expression;
    }

    public void setExpression(Expression<?> expression) {
        this.expression = expression;
    }

}
