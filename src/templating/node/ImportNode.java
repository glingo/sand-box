package templating.node;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.Writer;
import templating.expression.Expression;

public class ImportNode extends AbstractRenderableNode {

    private final Expression<?> importExpression;

    public ImportNode(int lineNumber, Expression<?> importExpression) {
        super(lineNumber);
        this.importExpression = importExpression;
    }

    @Override
    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
        self.importTemplate(context, (String) importExpression.evaluate(self, context));
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Expression<?> getImportExpression() {
        return importExpression;
    }

}
