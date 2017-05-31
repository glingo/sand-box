package templating.extension.escaper;

import templating.extension.Filter;
import com.marvin.component.util.ObjectUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EscapeFilter implements Filter {

    private String defaultStrategy = "html";

    private final List<String> argumentNames = new ArrayList<>();

    private final Map<String, EscapingStrategy> strategies;

    public EscapeFilter() {
        this.strategies = new HashMap<>();
        buildDefaultStrategies();
        argumentNames.add("strategy");
    }

    private void buildDefaultStrategies() {
        strategies.put("html", Escape::htmlText);

        strategies.put("js", Escape::jsString);

        strategies.put("css", Escape::cssString);

        strategies.put("html_attr", Escape::htmlText);

        strategies.put("url_param", Escape::uriParam);
    }

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

    @Override
    public Object apply(Object inputObject, Map<String, Object> args) {
        if (inputObject == null || inputObject instanceof SafeString) {
            return inputObject;
        }
        String input = ObjectUtils.nullSafeToString(inputObject);

        String strategy = defaultStrategy;

        if (args.get("strategy") != null) {
            strategy = (String) args.get("strategy");
        }

        if (!strategies.containsKey(strategy)) {
            throw new RuntimeException(String.format("Unknown escaping strategy [%s]", strategy));
        }

        return new SafeString(strategies.get(strategy).escape(input));
    }

    public String getDefaultStrategy() {
        return defaultStrategy;
    }

    public void setDefaultStrategy(String defaultStrategy) {
        this.defaultStrategy = defaultStrategy;
    }

    public void addEscapingStrategy(String name, EscapingStrategy strategy) {
        this.strategies.put(name, strategy);
    }
}
