package expressionLanguage.model.tree;

import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;
import templating.extension.NodeVisitor;

public class NamedArgumentNode extends Node {

    private final Expression<?> value;

    private final String name;

    public NamedArgumentNode(Position position, String name, Expression<?> value) {
        super(position);
        this.name = name;
        this.value = value;
    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public Expression<?> getValueExpression() {
        return value;
    }

    public String getName() {
        return name;
    }

}
