package expressionLanguage.model.tree;

import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;
import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.Writer;
import utils.ObjectUtils;

public class PrintNode extends Node {

    private Expression<?> expression;

    public PrintNode(Position position, Expression<?> expression) {
        super(position);
        this.expression = expression;
    }

//    @Override
//    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
//        Object var = expression.evaluate(self, context);
//        if (var != null) {
//            writer.write(ObjectUtils.nullSafeToString(var));
//        }
//    }
//
//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public Expression<?> getExpression() {
        return expression;
    }

    public void setExpression(Expression<?> expression) {
        this.expression = expression;
    }

}