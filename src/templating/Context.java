package templating;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;

public class Context {
    
    private Map<String, Object> model;
    
    private Map<String, Expression> expressions;

    
    public Optional<Object> evaluate(String name) {
        
        if (Objects.isNull(name)) {
            return Optional.ofNullable(name);
        }
        
        
        if (Objects.isNull(this.model)) {
            return Optional.empty();
        }
        
        getExpressions().forEach((n, e) -> {
            Matcher m = e.getPattern().matcher(name);
            if (m.find()) {
                String expressionName = m.group("name");
                String expression = m.group("expression");

                e.getConsumer().accept(expression, this);

                System.out.println(String.format("Expression : %s %s", name, expression));
                if (m.groupCount() > 2) {
                    String operator = m.group("operator");
                    String nested = m.group("nested");
                    System.out.println(String.format("%s %s", operator, nested));
                }
            }
        });
        
        return Optional.ofNullable(this.model.getOrDefault(name, name));
    }

    public Map<String, Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(Map<String, Expression> expressions) {
        this.expressions = expressions;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
