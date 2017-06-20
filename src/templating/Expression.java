package templating;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class Expression {
    
    private Pattern pattern;
    
    private BiConsumer<String, Context> consumer;

    public BiConsumer<String, Context> getConsumer() {
        return consumer;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setConsumer(BiConsumer<String, Context> consumer) {
        this.consumer = consumer;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
    
}
