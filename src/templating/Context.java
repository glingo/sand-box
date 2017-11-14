package templating;

import templating.token.TokenStream;
import converter.ConverterResolver;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import templating.token.Token;

public class Context {
    
    private Map<String, Object> model = new HashMap<>();
    
    private TokenStream stream;
    
    private String name;
    
    private List<Token> tokens;
    
    private ConverterResolver converter = new ConverterResolver();

    public Context(List<Token> tokens, String name) {
        this.tokens = tokens;
        this.name = name;
    }
    
    public <T> Optional<T> evaluate(String expression) {
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
    
    public <T> Optional<T> evaluate(String expression, Class<T> c) {
        return evaluate(expression);
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
    
    public TokenStream getStream(boolean force) {
        if (null == this.stream || force) {
            this.stream = new TokenStream(tokens, name);
        }
        return stream;
    }
    
    public TokenStream getStream() {
        return getStream(false);
    }
    
}
