package expressionLanguage.extension.core.filter;

import expressionLanguage.test.Test;
import java.util.List;
import java.util.Map;

public class NullTest implements Test {

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public boolean apply(Object input, Map<String, Object> args) {
        return input == null;
    }
}
