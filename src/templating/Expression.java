package templating;

import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.quote;

public class Expression {
    
//    private static final String COMMAND_PATTERN = "(?<open>%s+)(?<expression>.*)(?<close>%s+)";
    private final String EXPRESSION_PATTERN = "(?<name>%s+)(?<expression>.*)";
    private final String BINARY_EXPRESSION_PATTERN = "(?<name>%s+)(?<left>.*)(?<operator>%s?+)(?<right>.*)";
    
    private String name;
    private Pattern pattern;
    
    private BiConsumer<String, Context> consumer;

    public Expression(String name, BiConsumer<String, Context> consumer) {
        this.name = name;
        this.pattern = compile(quote(String.format(EXPRESSION_PATTERN, name)));
        this.consumer = consumer;
    }
    
    public void evalute(String value, Context context) {
//        Matcher m = getPattern().matcher(value);
//        if (m.lookingAt()) {
//            String expression = m.group("expression").trim();
////            getConsumer().accept(expression, context);
//        }
    }

//    public String getBasePattern() {
//        String base = //"^"
//            ""
//            .concat("(?<name>").concat(Pattern.quote(name)).concat("+)")
//            .concat("(?<expression>.*)");
//
//        return base;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
//    public Pattern getPattern() {
//        return Pattern.compile(getBasePattern());
//    }

    public void setConsumer(BiConsumer<String, Context> consumer) {
        this.consumer = consumer;
    }
}
