package templating.tokenParser;

import templating.expression.Expression;
import templating.node.RenderableNode;
import templating.node.SetNode;
import templating.parser.Parser;
import templating.token.Token;
import templating.token.TokenStream;
import templating.token.Type;

public class SetTokenParser extends AbstractTokenParser {

    @Override
    public RenderableNode parse(Token token, Parser parser) throws Exception {
        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        // skip the 'set' token
        stream.next();

        String name = parser.getExpressionParser().parseNewVariableName();

        stream.expect(Type.PUNCTUATION, "=");

        Expression<?> value = parser.getExpressionParser().parseExpression();

        stream.expect(Type.EXECUTE_END);

        return new SetNode(lineNumber, name, value);
    }

    @Override
    public String getTag() {
        return "set";
    }
}
