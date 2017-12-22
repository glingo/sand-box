package component.templating.token;

import java.util.Objects;
import component.templating.Position;

public class Token {
    
    private String type;
    
    private String value;
    
    private Position position;

    public Token(String type) {
        this.type = type;
    }

    public Token(String type, String value, Position position) {
        this.type = type;
        this.value = value;
        this.position = position;
    }
    
    public boolean isA(String type) {
        return this.type.equals(type);
    }
    
    public boolean isA(String type, String value) {
        return isA(type) && Objects.equals(this.value, value);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public static boolean isEOF(Token token) {
        return token.isA("EOF");
    }
    
    public static Token EOF() {
        return new Token("EOF");
    }
    
    public static Token text(String text, Position position) {
        return new Token("text", text, position);
    }

    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", value=" + value + ", position=" + position + '}';
    }
    
}
