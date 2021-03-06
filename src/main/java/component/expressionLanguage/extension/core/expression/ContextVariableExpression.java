package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.Expression;
import component.expressionLanguage.scope.ScopeChain;

public class ContextVariableExpression implements Expression<Object> {

    protected final String name;

    public ContextVariableExpression(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Object evaluate(EvaluationContext context) {
        ScopeChain scopeChain = context.getScopeChain();
        Object result = scopeChain.get(name);
        if (result == null && context.isStrictVariables() && !scopeChain.containsKey(name)) {
            String msg = String.format("Root attribute [%s] does not exist or can not be accessed and strict variables is set to true at line %s in file %s.", this.name);
            throw new IllegalArgumentException(msg);
        }
        return result;
    }

}
