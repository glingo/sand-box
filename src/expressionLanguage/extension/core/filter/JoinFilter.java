package expressionLanguage.extension.core.filter;

import expressionLanguage.filter.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Concatenates all entries of a collection, optionally glued together with a
 * particular character such as a comma.
 * 
 */
public class JoinFilter implements Filter {

    private final List<String> argumentNames = new ArrayList<>();

    public JoinFilter() {
        argumentNames.add("separator");
    }

//    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

    @Override
    public Object apply(Object input, Map<String, Object> args) {
        if (input == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        Collection<Object> inputCollection = (Collection<Object>) input;

        StringBuilder builder = new StringBuilder();

        String glue = null;
        if (args.containsKey("separator")) {
            glue = (String) args.get("separator");
        }

        boolean isFirst = true;
        for (Object entry : inputCollection) {

            if (!isFirst && glue != null) {
                builder.append(glue);
            }
            builder.append(entry);

            isFirst = false;
        }
        return builder.toString();
    }
}
