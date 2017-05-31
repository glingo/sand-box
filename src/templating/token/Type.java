package templating.token;

public enum Type {
    EOF, 
    TEXT, 
    EXECUTE_START, 
    EXECUTE_END, 
    PRINT_START, 
    PRINT_END, 
    NAME, 
    NUMBER, 
    STRING, 
    OPERATOR, 
    PUNCTUATION
}
