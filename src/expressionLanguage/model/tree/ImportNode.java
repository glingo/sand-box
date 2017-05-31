package expressionLanguage.model.tree;

import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;
import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import java.io.Writer;

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
