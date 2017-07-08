package templating.extention;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import templating.Source;
import templating.Token;

@FunctionalInterface
public interface TokenParser {
    
    void parse(Source source, List<Token> tokens);
    
    static TokenParser from(String name, String sequence) {
        Pattern pattern = Pattern.compile(Pattern.quote(sequence));
        return from(name, pattern);
    }
    
    static TokenParser from(String name, String... sequences) {
        String regex = Arrays.stream(sequences).collect(Collectors.joining("|"));
        return from(name, Pattern.compile(regex));
    }
    
    static TokenParser from(String name, Pattern pattern) {
        return (source, list) -> {
            Matcher matcher = pattern.matcher(source.toString());
            if(matcher.find() && matcher.lookingAt()) {
                list.add(new Token(name, source.substring(matcher.end()), source.getPosition()));
            }
        };
    }
}
