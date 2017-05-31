package templating.extension;

import java.util.Map;

public interface Function extends NamedArguments {

    Object execute(Map<String, Object> args);

}
