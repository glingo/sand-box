package templating.operator;

import templating.expression.UnaryExpression;

public class UnaryOperator extends Operator {
    
    protected int precedence;

    public UnaryOperator(String symbol, int precedence, Class<? extends UnaryExpression> nodeClass) {
        super(symbol, nodeClass);
        this.precedence = precedence;
    }
    
    public int getPrecedence(){
        return this.precedence;
    }
}
