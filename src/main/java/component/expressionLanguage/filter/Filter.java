package component.expressionLanguage.filter;

import java.util.Map;

public interface Filter {
    
    Object apply(Object input, Map<String, Object> args);
}
