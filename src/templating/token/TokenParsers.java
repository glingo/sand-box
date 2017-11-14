package templating.token;

import java.util.Arrays;
import java.util.regex.Pattern;

public interface TokenParsers {
    
    public static final Pattern REGEX_STRING = Pattern.compile("((\").*?(?<!\\\\)(\"))|((').*?(?<!\\\\)('))", Pattern.DOTALL);
    
    public static final Pattern REGEX_NUMBER = Pattern.compile("^[0-9]+(\\.[0-9]+)?");
        
    public static final String PUNCTUATION = "()[]{}?:.,|=";

    static TokenParser string() {
        return TokenParser.from("string", REGEX_STRING);
    }
    
    static TokenParser number() {
        return TokenParser.from("number", REGEX_NUMBER);
    }
    
    static TokenParser punctuation() {
        return TokenParser.in("punctuation", PUNCTUATION);
    }
    
    static TokenParser EOF() {
        return source -> {
            if (source.length() == 0) {
                return Arrays.asList(Token.EOF());
            }
            throw new Exception();
        };
    }
    
}
