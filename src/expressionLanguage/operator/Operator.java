package expressionLanguage.operator;

import expressionLanguage.expression.Expression;

public abstract class Operator {
    
//    protected String symbol;
    
    protected int precedence;
    protected Class<? extends Expression<?>> expression;

    public Operator(int precedence, Class<? extends Expression<?>> expression) {
        this.precedence = precedence;
        this.expression = expression;
    }
    
//    public Operator(String symbol, Class<? extends Expression<?>> expression) {
//        this.symbol = symbol;
//        this.expression = expression;
//    }
    
//    public String getSymbol(){
//        return this.symbol;
//    }

    public Class<? extends Expression<?>> getExpression() {
        return expression;
    }
    
    public int getPrecedence(){
        return this.precedence;
    }
}
