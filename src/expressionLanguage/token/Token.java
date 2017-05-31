package expressionLanguage.token;

import expressionLanguage.model.position.Position;
import java.util.Arrays;

public class Token {
    
    private String value;

    private Type type;

    private Position position;
    
    public Token(Type type, String value, Position position) {
        this.type = type;
        this.value = value;
        this.position = position;
    }
    
    public boolean test(Type type) {
        return test(type, new String[0]);
    }

    public boolean test(Type type, String... values) {
        boolean test = true;
        
        if (values.length > 0) {
            test = Arrays.asList(values).contains(this.value);
        }
        
        return test && this.type.equals(type);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Token[" + this.getType() + "](" + this.getValue() + ")";
    }
}
