package component.expressionLanguage.model.tree;

import component.expressionLanguage.expression.Expression;
import component.expressionLanguage.extension.core.expression.MapExpression;
import component.expressionLanguage.model.position.Position;

public class IncludeNode extends Node {

    private final Expression<?> includeExpression;

    private final MapExpression mapExpression;

    public IncludeNode(Position position, Expression<?> includeExpression, MapExpression mapExpression) {
        super(position);
        this.includeExpression = includeExpression;
        this.mapExpression = mapExpression;
    }

//    @Override
//    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
//        String templateName = (String) includeExpression.evaluate(self, context);
//
//        Map<?, ?> map = Collections.emptyMap();
//        if (this.mapExpression != null) {
//            map = this.mapExpression.evaluate(self, context);
//        }
//
//        if (templateName == null) {
//            String msg = String.format("The template name in an include tag evaluated to NULL. If the template name is static, make sure to wrap it in quotes. template [%s] at line %s in file %s.", templateName, getLineNumber(), self.getName());
//            throw new Exception(msg);
//        }
//        self.includeTemplate(writer, context, templateName, map);
//    }
//
//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public Expression<?> getIncludeExpression() {
        return includeExpression;
    }

}
