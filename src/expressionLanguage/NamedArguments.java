package expressionLanguage;

import java.util.List;

@FunctionalInterface
public interface NamedArguments {

    List<String> getArgumentNames();
}