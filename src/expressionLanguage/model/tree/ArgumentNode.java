package expressionLanguage.model.tree;

import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;

public class ArgumentNode extends Node {

    private String name;
    private final Expression<?> value;

    public ArgumentNode(Position position, Expression<?> value) {
        super(position);
        this.value = value;
    }
    
    public ArgumentNode(Position position, Expression<?> value, String name) {
        this(position, value);
        this.name = name;
    }

    public Expression<?> getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
