package expressionLanguage.filter;

import expressionLanguage.function.Function;
import java.util.Map;

public interface Filter extends NamedArguments {
    
    Object apply(Object input, Map<String, Object> args);
}
