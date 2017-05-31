package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;

public class LiteralBooleanExpression implements Expression<Boolean> {

    private final Boolean value;

    public LiteralBooleanExpression(Boolean value) {
        this.value = value;
    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    @Override
    public Boolean evaluate(EvaluationContext context) throws Exception {
        return value;
    }

}