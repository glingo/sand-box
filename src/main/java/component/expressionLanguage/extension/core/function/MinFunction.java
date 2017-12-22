package component.expressionLanguage.extension.core.function;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.function.Function;
import component.expressionLanguage.operator.OperatorUtils;
import java.util.List;
import java.util.Map;

public class MinFunction implements Function {

    @Override
    public String getName() {
        return "min";
    }

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public Object evaluate(EvaluationContext context, Map<String, Object> args) {
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
