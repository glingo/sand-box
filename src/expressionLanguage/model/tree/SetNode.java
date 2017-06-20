package expressionLanguage.model.tree;

import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;

public class SetNode extends Node {

    private final String name;

    private final Expression<?> value;

    public SetNode(Position position, String name, Expression<?> value) {
        super(position);
        this.name = name;
        this.value = value;
    }

//    @Override
//    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
//        context.getScopeChain().put(name, value.evaluate(self, context));
//    }

    public Expression<?> getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
