package component.expressionLanguage.token;

import component.expressionLanguage.model.position.Position;
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
    
    public boolean isA(Type type) {
        return this.type.equals(type);
    }

    public boolean isA(Type type, String... values) {
        boolean test = true;
        
        if (values.length > 0) {
            test = Arrays.asList(values).contains(this.value);
        }
        
        return test && isA(type);
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
    
    public boolean isEOF() {
        return Type.EOF.equals(getType());
    }

    @Override
    public String toString() {
        return "Token[" + this.getType() + "](" + this.getValue() + ")";
    }
}
