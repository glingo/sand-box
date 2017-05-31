package templating.tokenParser;

import templating.node.FlushNode;
import templating.node.RenderableNode;
import templating.parser.Parser;
import templating.token.Token;
import templating.token.TokenStream;
import templating.token.Type;

public class FlushTokenParser extends AbstractTokenParser {

    @Override
    public RenderableNode parse(Token token, Parser parser) throws Exception {

        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        // skip over the 'flush' token
        stream.next();

        stream.expect(Type.EXECUTE_END);

        return new FlushNode(lineNumber);
    }

    @Override
    public String getTag() {
        return "flush";
    }
}
