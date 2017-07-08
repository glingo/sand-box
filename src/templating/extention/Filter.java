package templating.extention;

import java.util.Map;

@FunctionalInterface
public interface Filter {
    
    <T> T apply(Object input, Map<String, Object> args);
}
