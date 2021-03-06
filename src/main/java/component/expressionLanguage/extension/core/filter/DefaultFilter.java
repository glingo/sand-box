package component.expressionLanguage.extension.core.filter;

import component.expressionLanguage.extension.core.test.EmptyTest;
import component.expressionLanguage.filter.Filter;
import component.expressionLanguage.test.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultFilter implements Filter {

    private final List<String> argumentNames = new ArrayList<>();

    public DefaultFilter() {
        argumentNames.add("default");
    }

//    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

    @Override
    public Object apply(Object input, Map<String, Object> args) {

        Object defaultObj = args.get("default");

        Test emptyTest = new EmptyTest();
        if (emptyTest.apply(input)) {
            return defaultObj;
        }
        return input;
    }

}
