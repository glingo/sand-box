package expressionLanguage.extension.core.token;

import expressionLanguage.model.tree.ArgumentsNode;
import expressionLanguage.model.tree.BodyNode;
import expressionLanguage.model.tree.MacroNode;
import expressionLanguage.model.tree.Node;
import expressionLanguage.token.Token;
import expressionLanguage.token.TokenStream;
import expressionLanguage.token.Type;
import expressionLanguage.token.parser.TokenParser;
import expressionLanguage.token.parser.TokenStreamParser;
import java.util.function.Predicate;

public class MacroTokenParser implements TokenParser {

    private final Predicate<Token> decideMacroEnd = (Token token) -> token.isA(Type.NAME, "endmacro");

    @Override
    public Node parse(Token token, TokenStreamParser parser) {

        TokenStream stream = parser.getStream();

        // skip over the 'macro' token
        stream.next();

        String macroName = stream.expect(Type.NAME).getValue();

        ArgumentsNode args = parser.getExpressionParser().parseArguments(true);

        stream.expect(Type.EXECUTE_END);

        // parse the body
        BodyNode body = parser.subparse(decideMacroEnd);

        // skip the 'endmacro' token
        stream.next();

        stream.expect(Type.EXECUTE_END);

        return new MacroNode(stream.current().getPosition(), macroName, args, body);
    }

    @Override
    public String getTag() {
        return "macro";
    }
}
