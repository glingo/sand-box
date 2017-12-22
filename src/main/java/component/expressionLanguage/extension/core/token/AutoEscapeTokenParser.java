package component.expressionLanguage.extension.core.token;

import component.expressionLanguage.model.position.Position;
import component.expressionLanguage.model.tree.AutoEscapeNode;
import component.expressionLanguage.model.tree.BodyNode;
import component.expressionLanguage.model.tree.Node;
import component.expressionLanguage.token.Token;
import component.expressionLanguage.token.TokenStream;
import component.expressionLanguage.token.Type;
import component.expressionLanguage.token.parser.TokenParser;
import component.expressionLanguage.token.parser.TokenStreamParser;
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
