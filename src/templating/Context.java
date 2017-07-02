package templating;

import converter.ConverterResolver;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Context {
    
    private Map<String, Object> model = new HashMap<>();
    
    private TokenStream stream;
    
    private ConverterResolver converter = new ConverterResolver();
    
    public <T> Optional<T> evaluate(String expression, Class<T> c) {
        if (Objects.isNull(this.model)) {
            return Optional.empty();
        }
        
//        expression = expression.trim();
        Object evaluation = this.converter.resolve(expression);
        if (null != evaluation) {
            Optional.of(evaluation);
        }
//        System.out.println(evaluation);

        return Optional.ofNullable((T) this.model.get(expression));
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public ConverterResolver getConverter() {
        return converter;
    }

    public TokenStream getStream() {
        return stream;
    }

    public void setStream(TokenStream stream) {
        this.stream = stream;
    }
    
}
