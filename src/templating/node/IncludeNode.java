package templating.node;

import templating.expression.Expression;
import templating.expression.MapExpression;
import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;


public class IncludeNode extends AbstractRenderableNode {

    private final Expression<?> includeExpression;

    private final MapExpression mapExpression;

    public IncludeNode(int lineNumber, Expression<?> includeExpression, MapExpression mapExpression) {
        super(lineNumber);
        this.includeExpression = includeExpression;
        this.mapExpression = mapExpression;
    }

    @Override
    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
        String templateName = (String) includeExpression.evaluate(self, context);

        Map<?, ?> map = Collections.emptyMap();
        if (this.mapExpression != null) {
            map = this.mapExpression.evaluate(self, context);
        }

        if (templateName == null) {
            String msg = String.format("The template name in an include tag evaluated to NULL. If the template name is static, make sure to wrap it in quotes. template [%s] at line %s in file %s.", templateName, getLineNumber(), self.getName());
            throw new Exception(msg);
        }
        self.includeTemplate(writer, context, templateName, map);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Expression<?> getIncludeExpression() {
        return includeExpression;
    }

}
