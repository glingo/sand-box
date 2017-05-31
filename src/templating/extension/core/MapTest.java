package templating.extension.core;

import templating.extension.Test;
import java.util.List;
import java.util.Map;

public class MapTest implements Test {

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public boolean apply(Object input, Map<String, Object> args) {
        return input instanceof Map;
    }

}
