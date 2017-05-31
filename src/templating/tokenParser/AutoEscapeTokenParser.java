package templating.tokenParser;

import templating.node.AutoEscapeNode;
import templating.node.BodyNode;
import templating.node.RenderableNode;
import templating.parser.Parser;
import templating.token.Token;
import templating.token.TokenStream;
import templating.token.Type;

public class AutoEscapeTokenParser extends AbstractTokenParser {

    @Override
    public RenderableNode parse(Token token, Parser parser) throws Exception {
        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        String strategy = null;
        boolean active = true;

        // skip over the 'autoescape' token
        stream.next();

        // did user specify active boolean?
        if (stream.current().test(Type.NAME)) {
            active = Boolean.parseBoolean(stream.current().getValue());
            stream.next();
        }

        // did user specify a strategy?
        if (stream.current().test(Type.STRING)) {
            strategy = stream.current().getValue();
            stream.next();
        }

        stream.expect(Type.EXECUTE_END);

        // now we parse the block body
        BodyNode body = parser.subparse((Token token1) -> token1.test(Type.NAME, "endautoescape"));

        // skip the 'endautoescape' token
        stream.next();

        stream.expect(Type.EXECUTE_END);

        return new AutoEscapeNode(lineNumber, body, active, strategy);
    }

    @Override
    public String getTag() {
        return "autoescape";
    }
}
