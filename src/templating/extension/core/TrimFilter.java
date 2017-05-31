package templating.extension.core;

import templating.extension.Filter;

import java.util.List;
import java.util.Map;

public class TrimFilter implements Filter {

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public Object apply(Object input, Map<String, Object> args) {
        if (input == null) {
            return null;
        }
        String str = (String) input;
        return str.trim();
    }

}
