package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;

public class LiteralLongExpression implements Expression<Long> {

    private final Long value;

    public LiteralLongExpression(Long value) {
        this.value = value;
    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    @Override
    public Long evaluate(EvaluationContext context) throws Exception {
        return value;
    }

}
