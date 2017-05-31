package expressionLanguage.extension.core.filter;

import expressionLanguage.filter.Filter;
import java.util.List;
import java.util.Map;

public class AbsFilter implements Filter {

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public Number apply(Object input, Map<String, Object> args) {
        if (input == null) {
            throw new IllegalArgumentException("Can not pass null value to \"abs\" filter.");
        }
        if (input instanceof Long) {
            return Math.abs((Long) input);
        } else {
            return Math.abs((Double) input);
        }
    }

}
