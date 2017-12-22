package component.templating.extention;

import java.util.Map;

@FunctionalInterface
public interface Function {
    
    <T> T apply(Map<String, Object> args);
}
