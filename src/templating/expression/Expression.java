package templating.expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import templating.Context;
import templating.token.Token;
import templating.token.TokenStream;

@FunctionalInterface
public interface Expression<T> {

    T evaluate(Context context);
    
    static Expression parse(TokenStream stream) throws Exception {
        Token token = stream.current();
        
        if (token.isA("punctuation", "[")) {
            return array(stream);
        }
        
        return subParse(stream);
    }
    
    static Expression subParse(TokenStream stream) throws Exception {
        Token token = stream.current();
        switch(token.getType()) {
            case "name":
                switch(token.getValue()) {
                    case "true":
                    case "TRUE":
                        return literalBoolean(Boolean.TRUE);
                    case "false":
                    case "FALSE":
                        return literalBoolean(Boolean.FALSE);
                    case "none":
                    case "NONE":
                    case "null":
                    case "NULL":
                        return literalNull();
                    default:
                        if (stream.peek().isA("punctuation", "(")) {
                            // function call
                            return functionName(token.getValue());
                        }
                        return varName(token.getValue());
                }
            case "number":
                final String numberValue = token.getValue();
                if (numberValue.contains(".")) {
                    return literalDouble(numberValue);
                }
                return literalLong(numberValue);
            case "string":
                return literalString(token.getValue());
            default:
                String msg = String.format("Unexpected token \"%s\" of value \"%s\" at %s.", token.getType(), token.getValue(), token.getPosition());
                throw new Exception(msg);
        }
    }
    
    static Expression functionName(String value) {
        return context -> value;
    }
    
    static Expression varName(String value) {
        return context -> context.evaluate(value);
    }
    
    static Expression<Boolean> literalNull() {
        return context -> null;
    }
    
    static Expression<Boolean> literalBoolean(Boolean value) {
        return context -> value;
    }
    
    static Expression<String> literalString(String value) {
        return context -> value;
    }
    
    static Expression<Double> literalDouble(String value) {
        return context -> Double.parseDouble(value);
    }
    
    static Expression<Long> literalLong(String value) {
        return context -> Long.parseLong(value);
    }
    
    static Expression<List> emptyList() {
        return context -> Collections.emptyList();
    }
    
    static Expression<List> array(TokenStream stream) throws Exception {
        stream.expect("punctuation", "[");
        
        if (stream.current().isA("punctuation", "]")) {
            stream.next();
            return emptyList();
        }
        List<Expression> expressions = new ArrayList();

        while (!stream.current().isA("punctuation", "]")) {
            expressions.add(subParse(stream));
            stream.expect("punctuation", ",");
        }
        
        stream.expect("punctuation", "]");
            
        return (context) -> {
            List<Object> returnValues = new ArrayList<>(expressions.size());
            expressions.stream()
                    .map((expr) -> expr == null ? null : expr.evaluate(context))
                    .forEach((value) -> {
                returnValues.add(value);
            });
            return returnValues;
        };
    }
}
