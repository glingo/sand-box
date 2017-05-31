package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.NodeVisitor;
import templating.node.ArgumentsNode;

/**
 * The right hand side to the filter expression.
 *
 */
public class FilterInvocationExpression implements Expression<Object> {

    private final String filterName;

    private final ArgumentsNode args;

    private final int lineNumber;

    public FilterInvocationExpression(String filterName, ArgumentsNode args, int lineNumber) {
        this.filterName = filterName;
        this.args = args;
        this.lineNumber = lineNumber;
    }

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {
        // see FilterExpression.java
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    public ArgumentsNode getArgs() {
        return args;
    }

    public String getFilterName() {
        return filterName;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

}
