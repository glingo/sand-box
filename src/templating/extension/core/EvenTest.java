package templating.extension.core;

import templating.extension.Test;
import java.util.List;
import java.util.Map;

public class EvenTest implements Test {

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public boolean apply(Object input, Map<String, Object> args) {
        if (input == null) {
            throw new IllegalArgumentException("Can not pass null value to \"even\" test.");
        }

        if (input instanceof Integer) {
            return ((Integer) input) % 2 == 0;
        } else {
            return ((Long) input) % 2 == 0;
        }
    }
}
