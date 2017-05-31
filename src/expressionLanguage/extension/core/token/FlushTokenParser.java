package expressionLanguage.extension.core.token;

import expressionLanguage.model.position.Position;
import expressionLanguage.model.tree.FlushNode;
import expressionLanguage.model.tree.Node;
import expressionLanguage.parser.Parser;
import expressionLanguage.token.Token;
import expressionLanguage.token.TokenStream;
import expressionLanguage.token.Type;
import expressionLanguage.token.parser.TokenParser;

public class FlushTokenParser implements TokenParser {

    @Override
    public Node parse(Token token, Parser parser) throws Exception {

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
