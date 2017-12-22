package component.expressionLanguage.extension.core.expression;

import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.expression.Expression;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapExpression implements Expression<Map<?, ?>> {

    // FIXME should keys be of any type?
    private final Map<Expression<?>, Expression<?>> entries;

    public MapExpression() {
        this.entries = Collections.emptyMap();
    }

    public MapExpression(Map<Expression<?>, Expression<?>> entries) {
        if (entries == null) {
            this.entries = Collections.emptyMap();
        } else {
            this.entries = entries;
        }
    }

    @Override
    public Map<?, ?> evaluate(EvaluationContext context) {
        Map<Object, Object> returnEntries = new HashMap<>(Long.valueOf(Math.round(Math.ceil(entries.size() / 0.75)))
                .intValue());
        entries.entrySet().stream().forEach((entry) -> {
            Expression<?> keyExpr = entry.getKey();
            Expression<?> valueExpr = entry.getValue();
            Object key = keyExpr == null ? null : keyExpr.evaluate(context);
            Object value = valueExpr == null ? null : valueExpr.evaluate(context);
            returnEntries.put(key, value);
        });
        return returnEntries;
    }

}
