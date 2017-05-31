package templating.expression;

import templating.EvaluationContext;
import templating.scope.ScopeChain;
import templating.template.Template;
import templating.extension.NodeVisitor;


public class ContextVariableExpression implements Expression<Object> {

    protected final String name;

    private final int lineNumber;

    public ContextVariableExpression(String name, int lineNumber) {
        this.name = name;
        this.lineNumber = lineNumber;
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {
        ScopeChain scopeChain = context.getScopeChain();
        Object result = scopeChain.get(name);
        if (result == null && context.isStrictVariables() && !scopeChain.containsKey(name)) {
            String msg = String.format("Root attribute [%s] does not exist or can not be accessed and strict variables is set to true at line %s in file %s.", this.name, this.lineNumber, self.getName());
            throw new Exception(msg);
        }
        return result;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

}
