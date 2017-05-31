package expressionLanguage.model.tree;

import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;
import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.Writer;

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

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public Expression<?> getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
