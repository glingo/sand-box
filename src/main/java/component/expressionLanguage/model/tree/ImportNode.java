package component.expressionLanguage.model.tree;

import component.expressionLanguage.expression.Expression;
import component.expressionLanguage.model.position.Position;

public class ImportNode extends Node {

    private final Expression<?> importExpression;

    public ImportNode(Position position, Expression<?> importExpression) {
        super(position);
        this.importExpression = importExpression;
    }

//    @Override
//    public void render(Template self, Writer writer, EvaluationContext context) throws Exception {
//        self.importTemplate(context, (String) importExpression.evaluate(self, context));
//    }
//
//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public Expression<?> getImportExpression() {
        return importExpression;
    }

}
