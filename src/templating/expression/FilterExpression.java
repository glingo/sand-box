package templating.expression;

import templating.EvaluationContext;
import templating.template.Template;
import templating.extension.Filter;
import templating.extension.core.DefaultFilter;
import templating.extension.escaper.EscapeFilter;
import templating.extension.escaper.SafeString;
import java.util.Map;
import javax.management.AttributeNotFoundException;
import templating.node.ArgumentsNode;

public class FilterExpression extends BinaryExpression<Object> {

    /**
     * Save the filter instance on the first evaluation.
     */
    private Filter filter = null;

    public FilterExpression() {
        super();

    }

    @Override
    public Object evaluate(Template self, EvaluationContext context) throws Exception {

        FilterInvocationExpression filterInvocation = (FilterInvocationExpression) getRightExpression();
        ArgumentsNode args = filterInvocation.getArgs();
        String filterName = filterInvocation.getFilterName();

        if (this.filter == null) {
            this.filter = context.getExtensionRegistry().getFilter(filterInvocation.getFilterName());
        }

        if (filter == null) {
            String msg = String.format("Filter [%s] does not exist at line %s in file %s.", filterName, this.getLineNumber(), self.getName());
            throw new Exception(msg);
        }

        Map<String, Object> namedArguments = args.getArgumentMap(self, context, filter);

        // This check is not nice, because we use instanceof. However this is
        // the only filter which should not fail in strict mode, when the variable
        // is not set, because this method should exactly test this. Hence a
        // generic solution to allow other tests to reuse this feature make no sense
        Object input;
        if (filter instanceof DefaultFilter) {
            try {
                input = getLeftExpression().evaluate(self, context);
            } catch (AttributeNotFoundException ex) {
                input = null;
            }
        } else {
            input = getLeftExpression().evaluate(self, context);
        }

        if (input instanceof SafeString && !(filter instanceof EscapeFilter)) {
            input = input.toString();
        }

        return filter.apply(input, namedArguments);
    }
}
