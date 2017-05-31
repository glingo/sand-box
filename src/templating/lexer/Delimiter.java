package templating.lexer;

public enum Delimiter {
    
    COMMENT("{#", "#}"),
    EXECUTE("{%", "%}"),
    PRINT("{{", "}}");
    
    private final String open;
    private final String close;
    
    private Delimiter(String open, String close) {
        this.open = open;
        this.close = close;
    }

    public String getOpen() {
        return open;
    }

    public String getClose() {
        return close;
    }
}
