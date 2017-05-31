package expressionLanguage.extension.core.filter;

import expressionLanguage.function.Function;
import templating.operator.OperatorUtils;
import java.util.List;
import java.util.Map;

public class MinFunction implements Function {

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public Object execute(Map<String, Object> args) {
        Object min = null;

        int i = 0;

        while (args.containsKey(String.valueOf(i))) {

            Object candidate = args.get(String.valueOf(i));
            i++;

            if (min == null) {
                min = candidate;
                continue;
            }
            if (OperatorUtils.lt(candidate, min)) {
                min = candidate;
            }
        }
        return min;
    }

}
