package expressionLanguage.extension.core.token;

import expressionLanguage.model.position.Position;
import expressionLanguage.model.tree.AutoEscapeNode;
import expressionLanguage.model.tree.BodyNode;
import expressionLanguage.model.tree.Node;
import expressionLanguage.token.Token;
import expressionLanguage.token.TokenStream;
import expressionLanguage.token.Type;
import expressionLanguage.token.parser.TokenParser;
import expressionLanguage.token.parser.TokenStreamParser;
import java.util.function.Predicate;

public class AutoEscapeTokenParser implements TokenParser {

    @Override
    public String getTag() {
        return "autoescape";
    }
    
    @Override
    public Node parse(Token token, TokenStreamParser parser) {
        TokenStream stream = parser.getStream();
        Position position = token.getPosition();

        String strategy = null;
        boolean active = true;

        // skip over the 'autoescape' token
        stream.next();

        // did user specify active boolean?
        if (stream.current().isA(Type.NAME)) {
            active = Boolean.parseBoolean(stream.current().getValue());
            stream.next();
        }

        // did user specify a strategy?
        if (stream.current().isA(Type.STRING)) {
            strategy = stream.current().getValue();
            stream.next();
        }

        stream.expect(Type.EXECUTE_END);

        // now we parse the block body
        BodyNode body = parser.subparse((Token token1) -> token1.isA(Type.NAME, "endautoescape"));

        // skip the 'endautoescape' token
        stream.next();

        stream.expect(Type.EXECUTE_END);

        return new AutoEscapeNode(position, body, active, strategy);
    }


}
