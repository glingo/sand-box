package expressionLanguage.filter;

import expressionLanguage.EvaluationContext;
import expressionLanguage.extension.core.test.EmptyTest;
import expressionLanguage.test.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DefaultFilter implements Filter {

    private final List<String> argumentNames = new ArrayList<>();

    public DefaultFilter() {
        argumentNames.add("default");
    }
    
    @Override
    public String getName() {
        return "default";
    }

    @Override
    public Object evaluate(EvaluationContext context, Map<String, Object> args) {
        
    }
    

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

    @Override
    public Object apply(Object input, Map<String, Object> args) {
        return filter(input, args.get("default"));
    }
    
    public Object filter(Object input, Object orElse) {
        if (new EmptyTest().apply(input)) {
            return orElse;
        }
        
        return input;
    }

}
