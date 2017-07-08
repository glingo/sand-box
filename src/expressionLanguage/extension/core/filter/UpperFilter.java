package expressionLanguage.extension.core.filter;

import expressionLanguage.filter.Filter;
import java.util.List;
import java.util.Map;

public class UpperFilter implements Filter {

//    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public Object apply(Object input, Map<String, Object> args) {
        if (input == null) {
            return null;
        }
        return ((String)input).toUpperCase();
    }

}
