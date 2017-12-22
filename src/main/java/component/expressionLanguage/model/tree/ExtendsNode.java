package component.expressionLanguage.model.tree;

import component.expressionLanguage.expression.Expression;
import component.expressionLanguage.model.position.Position;

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
