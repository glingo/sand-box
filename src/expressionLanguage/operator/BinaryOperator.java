package expressionLanguage.operator;

import expressionLanguage.expression.BinaryExpression;

public class BinaryOperator extends Operator {
    
    protected Associativity associativity;

    public BinaryOperator(int precedence, Class<? extends BinaryExpression<?>> nodeClass, Associativity associativity) {
        super(precedence, nodeClass);
        this.associativity = associativity;
    }
    
    public Associativity getAssociativity() {
        return associativity;
    }
}
