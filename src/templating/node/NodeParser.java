package templating.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import templating.expression.Expression;
import templating.token.Token;
import templating.token.TokenStream;

@FunctionalInterface
public interface NodeParser {
    
    List<Node> parse(TokenStream stream) throws Exception;
    
    static NodeParser fromTypes() {
        
        Map<String, NodeParser> parsers = new HashMap<>();
        
        parsers.put("text", stream -> {
            Token current = stream.current();
            List<Node> result = new ArrayList<>();
            result.add(new TextNode(current.getValue()));
            return result;
        });
        
        parsers.put("print_open", stream -> {
            stream.next();
            List<Node> result = new ArrayList<>();
            result.add(new PrintNode(Expression.parse(stream)));
            return result;
        });
        
        parsers.put("execute_open", stream -> {
            Token token = stream.next();
            List<Node> result = new ArrayList<>();
            
            if (!token.isA("name")) {
                throw new Exception();
            }
            
            result.add(new ExecuteNode(Expression.parse(stream)));
            return result;
        });
        
        return stream -> {
            List<Node> result = new LinkedList<>();
            
            while (!Token.isEOF(stream.current())) {
                Token token = stream.current();
                NodeParser parser = parsers.get(token.getType());
                
                if (null != parser) {
                    result.addAll(parser.parse(stream));
                }
                
                stream.next();
            }
            
            return result;
        };
    }
}
