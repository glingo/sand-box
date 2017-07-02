package templating;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.quote;

@FunctionalInterface
public interface TokenParser {
    
    void parse(Source source, List<Token> tokens);
    
    static TokenParser group(String name, String start, String end) {
        return (source, tokens) -> {
            Pattern pattern = compile("(?<open>".concat(quote(start)).concat("+)")
                    .concat("(?<expression>.*?)")
                    .concat("(?<close>").concat(quote(end)).concat("+)"));
            Matcher matcher = pattern.matcher(source.toString());
            if(matcher.find() && matcher.lookingAt()) {
                tokens.add(new Token(name + "_open", matcher.group("open"), source.getPosition()));
                tokens.add(new Token("expression", matcher.group("expression"), source.getPosition()));
                tokens.add(new Token(name + "_close", matcher.group("close"), source.getPosition()));
                source.advance(matcher.end());
            }
        };
    };
    
    
}
