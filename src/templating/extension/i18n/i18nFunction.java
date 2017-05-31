package templating.extension.i18n;

import templating.EvaluationContext;
import templating.extension.Function;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class i18nFunction implements Function {

    private final List<String> argumentNames = new ArrayList<>();

    public i18nFunction() {
        argumentNames.add("bundle");
        argumentNames.add("key");
        argumentNames.add("params");
    }

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

    @Override
    public Object execute(Map<String, Object> args) {
        String basename = (String) args.get("bundle");
        String key = (String) args.get("key");
        Object params = args.get("params");

        EvaluationContext context = (EvaluationContext) args.get("_context");
        Locale locale = context.getLocale();

        ResourceBundle bundle = ResourceBundle.getBundle(basename, locale, new UTF8Control());
        Object phraseObject = bundle.getObject(key);

        if (phraseObject != null && params != null) {
            if (params instanceof List) {
                List<?> list = (List<?>) params;
                return MessageFormat.format(phraseObject.toString(), list.toArray());
            } else {
                return MessageFormat.format(phraseObject.toString(), params);
            }
        }

        return phraseObject;
    }

}
