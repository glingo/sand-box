package expressionLanguage.extension.core.filter;

import expressionLanguage.filter.Filter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Sort list items in the reverse order
 */
public class RsortFilter implements Filter {

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public List<Comparable> apply(Object input, Map<String, Object> args) {
        if (input == null) {
            return null;
        }
        List<Comparable> collection = (List<Comparable>) input;
        Collections.sort(collection, Collections.reverseOrder());
        return collection;
    }

}
