package expressionLanguage.token;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public enum Type {
    EOF(), 
    TEXT, 
    EXECUTE_START, 
    EXECUTE_END, 
    PRINT_START(compile("{{")), 
    PRINT_END, 
    NAME(compile("^[a-zA-Z_][a-zA-Z0-9_]*")), 
    NUMBER(compile("^[0-9]+(\\.[0-9]+)?")), 
    STRING(compile("((\").*?(?<!\\\\)(\"))|((').*?(?<!\\\\)('))", Pattern.DOTALL)), 
    OPERATOR, 
    PUNCTUATION(compile("()[]{}?:.,|="));
    
    private Pattern pattern;

    private Type(Pattern regex) {
        this.pattern = Pattern.compile(regex);
    }
    
    
}
