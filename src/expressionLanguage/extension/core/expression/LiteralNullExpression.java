package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;

public class LiteralNullExpression implements Expression<Object> {

    public LiteralNullExpression() {
    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    @Override
    public Object evaluate(EvaluationContext context) throws Exception {
        return null;
    }

}
