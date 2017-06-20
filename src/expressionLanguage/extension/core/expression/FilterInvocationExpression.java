package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.Expression;
import expressionLanguage.model.tree.ArgumentsNode;

/**
 * The right hand side to the filter expression.
 *
 */
public class FilterInvocationExpression implements Expression<Object> {

    private final String filterName;

    private final ArgumentsNode args;
    
    public FilterInvocationExpression(String filterName, ArgumentsNode args) {
        this.filterName = filterName;
        this.args = args;
    }

    @Override
    public Object evaluate(EvaluationContext context) {
        // see FilterExpression.java
        throw new UnsupportedOperationException();
    }

//    @Override
//    public void accept(NodeVisitor visitor) {
//        visitor.visit(this);
//    }

    public ArgumentsNode getArgs() {
        return args;
    }

    public String getFilterName() {
        return filterName;
    }

//    @Override
//    public int getLineNumber() {
//        return this.lineNumber;
//    }

}
