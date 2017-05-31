package templating.node;

import templating.expression.Expression;
import templating.extension.NodeVisitor;

public class PositionalArgumentNode implements Node {

    private final Expression<?> value;

    public PositionalArgumentNode(Expression<?> value) {
        this.value = value;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Expression<?> getValueExpression() {
        return value;
    }

}
