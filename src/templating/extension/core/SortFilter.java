package templating.extension.core;

import templating.extension.Filter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SortFilter implements Filter {

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
        Collections.sort(collection);
        return collection;
    }

}
