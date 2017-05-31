package expressionLanguage.model.tree;

import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;

public class PositionalArgumentNode extends Node {

    private final Expression<?> value;

    public PositionalArgumentNode(Position position, Expression<?> value) {
        super(position);
        this.value = value;
    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public Expression<?> getValueExpression() {
        return value;
    }

}
