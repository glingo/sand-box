package templating.tokenParser;

import templating.expression.Expression;
import templating.node.BodyNode;
import templating.node.ForNode;
import templating.node.RenderableNode;
import templating.parser.Parser;
import templating.token.Token;
import templating.token.TokenStream;
import templating.token.Type;

public class ForTokenParser extends AbstractTokenParser {


    @Override
    public RenderableNode parse(Token token, Parser parser) throws Exception {
        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        // skip the 'for' token
        stream.next();

        // get the iteration variable
        String iterationVariable = parser.getExpressionParser().parseNewVariableName();

        stream.expect(Type.NAME, "in");

        // get the iterable variable
        Expression<?> iterable = parser.getExpressionParser().parseExpression();

        stream.expect(Type.EXECUTE_END);

        BodyNode body = parser.subparse((Token subToken) -> subToken.test(Type.NAME, "else", "endfor"));

        BodyNode elseBody = null;

        if (stream.current().test(Type.NAME, "else")) {
            // skip the 'else' token
            stream.next();
            stream.expect(Type.EXECUTE_END);
            elseBody = parser.subparse((Token subToken) -> subToken.test(Type.NAME, "endfor"));
        }

        // skip the 'endfor' token
        stream.next();

        stream.expect(Type.EXECUTE_END);

        return new ForNode(lineNumber, iterationVariable, iterable, body, elseBody);
    }
    
    @Override
    public String getTag() {
        return "for";
    }
}
