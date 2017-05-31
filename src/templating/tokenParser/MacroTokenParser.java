package templating.tokenParser;

import templating.node.ArgumentsNode;
import templating.node.BodyNode;
import templating.node.MacroNode;
import templating.node.RenderableNode;
import templating.parser.Parser;
import templating.parser.StoppingCondition;
import templating.token.Token;
import templating.token.TokenStream;
import templating.token.Type;


public class MacroTokenParser extends AbstractTokenParser {

    private final StoppingCondition decideMacroEnd = (Token token) -> token.test(Type.NAME, "endmacro");

    @Override
    public RenderableNode parse(Token token, Parser parser) throws Exception {

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

        return new MacroNode(macroName, args, body);
    }

    @Override
    public String getTag() {
        return "macro";
    }
}
