package expressionLanguage.test;

import expressionLanguage.NamedArguments;
import java.util.Map;

public interface Test extends NamedArguments {

    boolean apply(Object input, Map<String, Object> args);
}
