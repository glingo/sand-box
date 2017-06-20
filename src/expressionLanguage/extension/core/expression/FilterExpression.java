package expressionLanguage.extension.core.expression;

import expressionLanguage.EvaluationContext;
import expressionLanguage.expression.BinaryExpression;
import expressionLanguage.filter.Filter;
import expressionLanguage.model.tree.ArgumentsNode;
import java.util.Map;
import java.util.Objects;

public class FilterExpression extends BinaryExpression<Object> {

    /**
     * Save the filter instance on the first evaluation.
     */
    private Filter filter = null;

    public FilterExpression() {
        super();
    }

    @Override
    public Object evaluate(EvaluationContext context) {

        FilterInvocationExpression filterInvocation = (FilterInvocationExpression) getRightExpression();
        ArgumentsNode args = filterInvocation.getArgs();
        String filterName = filterInvocation.getFilterName();

        if (this.filter == null) {
            this.filter = context.getExtensionRegistry().getFilter(filterInvocation.getFilterName());
        }

        if (filter == null) {
            String msg = String.format("Filter [%s] does not exist at line %s in file %s.", filterName);
            throw new IllegalStateException(msg);
        }

        Map<String, Object> namedArguments = args.getArgumentMap(context, filter);

        Object input = getLeftExpression().evaluate(context);

        return filter.apply(Objects.toString(input), namedArguments);
    }
}
