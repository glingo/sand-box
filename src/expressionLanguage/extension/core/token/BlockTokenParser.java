package expressionLanguage.extension.core.token;

import expressionLanguage.model.position.Position;
import expressionLanguage.model.tree.BlockNode;
import expressionLanguage.model.tree.BodyNode;
import expressionLanguage.model.tree.Node;
import expressionLanguage.token.parser.Parser;
import expressionLanguage.token.Token;
import expressionLanguage.token.TokenStream;
import expressionLanguage.token.Type;
import expressionLanguage.token.parser.TokenParser;
import expressionLanguage.token.parser.TokenStreamParser;

public class BlockTokenParser implements TokenParser {

    @Override
    public Node parse(Token token, TokenStreamParser parser) {
        TokenStream stream = parser.getStream();
        Position position = token.getPosition();

        // skip over the 'block' token to the name token
        Token blockName = stream.next();

        // expect a name or string for the new block
        if (!blockName.isA(Type.NAME) && !blockName.isA(Type.STRING)) {

            // we already know an error has occurred but let's just call the
            // typical "expect" method so that we know a proper error
            // message is given to user
            stream.expect(Type.NAME);
        }

        // get the name of the new block
        String name = blockName.getValue();

        // skip over name
        stream.next();

        stream.expect(Type.EXECUTE_END);

        parser.pushBlockStack(name);

        // now we parse the block body
        BodyNode blockBody = parser.subparse((Token token1) -> token1.isA(Type.NAME, "endblock"));
        parser.popBlockStack();

        // skip the 'endblock' token
        stream.next();

        // check if user included block name in endblock
        Token current = stream.current();
        if (current.isA(Type.NAME, name) || current.isA(Type.STRING, name)) {
            stream.next();
        }

        stream.expect(Type.EXECUTE_END);
        return new BlockNode(position, name, blockBody);
    }

    @Override
    public String getTag() {
        return "block";
    }
}
