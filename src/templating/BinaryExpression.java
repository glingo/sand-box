package templating;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BinaryExpression extends Expression {
    
    private String[] operators;
    private Expression left;
    private Expression right;

    public BinaryExpression(String name, String[] operators, BiConsumer consumer) {
        super(name, consumer);
        this.operators = operators;
    }
    
    @Override
    public void evalute(String value, Context context) {
        Matcher m = getPattern().matcher(value);
        
        String matchable = Arrays.stream(this.operators)
                .map(operator -> Pattern.quote(operator))
                .collect(Collectors.joining("|"));

        Pattern pattern = Pattern.compile("(?<left>.*)"
            .concat("(?<operator>").concat(matchable).concat("?+)")
            .concat("(?<right>.*)"));

        if (m.lookingAt()) {
            
            String expression = m.group("expression");
            Matcher matcher = pattern.matcher(expression);
            
            if (matcher.lookingAt() && matcher.groupCount() > 2) {
                this.left = new Expression("left", (a, b) -> {});
                this.right = new Expression("right", (a, b) -> {});
            }
            
            getConsumer().accept(expression, context);
        }
    }
}
