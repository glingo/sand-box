package templating.extention;

import java.util.Map;

@FunctionalInterface
public interface Test {
    
    boolean apply(Object input, Map<String, Object> args);
}
