package templating.node;

import templating.expression.Expression;
import templating.extension.NodeVisitor;

public class NamedArgumentNode implements Node {

    private final Expression<?> value;

    private final String name;

    public NamedArgumentNode(String name, Expression<?> value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public Expression<?> getValueExpression() {
        return value;
    }

    public String getName() {
        return name;
    }

}
