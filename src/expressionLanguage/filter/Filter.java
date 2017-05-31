package expressionLanguage.filter;

import expressionLanguage.NamedArguments;
import java.util.Map;

public interface Filter extends NamedArguments {

    Object apply(Object input, Map<String, Object> args);
}
