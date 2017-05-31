package templating.extension;

import java.util.Map;

public interface Filter extends NamedArguments {

    Object apply(Object input, Map<String, Object> args);
}
