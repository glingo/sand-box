package component.expressionLanguage.operator;

import component.expressionLanguage.expression.UnaryExpression;

public class UnaryOperator extends Operator {
    
    public UnaryOperator(int precedence, Class<? extends UnaryExpression> nodeClass) {
        super(precedence, nodeClass);
    }
}
