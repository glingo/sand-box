package component.expressionLanguage.extension.core.token;

import component.expressionLanguage.model.position.Position;
import component.expressionLanguage.model.tree.FlushNode;
import component.expressionLanguage.model.tree.Node;
import component.expressionLanguage.token.parser.Parser;
import component.expressionLanguage.token.Token;
import component.expressionLanguage.token.TokenStream;
import component.expressionLanguage.token.Type;
import component.expressionLanguage.token.parser.TokenParser;
import component.expressionLanguage.token.parser.TokenStreamParser;

public class FlushTokenParser implements TokenParser {

    @Override
    public Node parse(Token token, TokenStreamParser parser) {

        TokenStream stream = parser.getStream();
        Position position = token.getPosition();

        // skip over the 'flush' token
        stream.next();

        stream.expect(Type.EXECUTE_END);

        return new FlushNode(position);
    }

    @Override
    public String getTag() {
        return "flush";
    }
}
