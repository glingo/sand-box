package expressionLanguage.operator;

import expressionLanguage.expression.BinaryExpression;

public class BinaryOperator extends Operator {
    
    protected int precedence;
    protected Associativity associativity;

    public BinaryOperator(String symbol, int precedence, Class<? extends BinaryExpression<?>> nodeClass, Associativity associativity) {
        super(symbol, nodeClass);
        this.associativity = associativity;
        this.precedence = precedence;
    }
    
    public Associativity getAssociativity() {
        return associativity;
    }
    
    public int getPrecedence(){
        return this.precedence;
    }
}
