package expressionLanguage.model.tree;

import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;
import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.Writer;

public class ExtendsNode extends Node {

    Expression<?> parentExpression;

    public ExtendsNode(Position position, Expression<?> parentExpression) {
        super(position);
        this.parentExpression = parentExpression;
    }

//    @Override
//    public void render(final Template self, Writer writer, final EvaluationContext context) throws Exception {
//        self.setParent(context, (String) parentExpression.evaluate(self, context));
//    }
//
//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public Expression<?> getParentExpression() {
        return parentExpression;
    }
}
