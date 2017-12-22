package component.expressionLanguage.model.tree;

import component.expressionLanguage.expression.Expression;
import component.expressionLanguage.model.position.Position;

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
