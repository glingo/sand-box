package templating.tokenParser;

import templating.expression.Expression;
import templating.expression.MapExpression;
import templating.node.IncludeNode;
import templating.node.RenderableNode;
import templating.parser.Parser;
import templating.token.Token;
import templating.token.TokenStream;
import templating.token.Type;

public class IncludeTokenParser extends AbstractTokenParser {

    @Override
    public RenderableNode parse(Token token, Parser parser) throws Exception {

        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        // skip over the 'include' token
        stream.next();

        Expression<?> includeExpression = parser.getExpressionParser().parseExpression();

        Token current = stream.current();
        MapExpression mapExpression = null;

        // We check if there is an optional 'with' parameter on the include tag.
        if (current.getType().equals(Type.NAME) && current.getValue().equals("with")) {

            // Skip over 'with'
            stream.next();

            Expression<?> parsedExpression = parser.getExpressionParser().parseExpression();

            if (parsedExpression instanceof MapExpression) {
                mapExpression = (MapExpression) parsedExpression;
            } else {
                String msg = String.format("Unexpected expression '%1s' at line %s in file %s.", parsedExpression .getClass().getCanonicalName(), token.getLineNumber(), stream.getFilename());
                throw new Exception(msg);
            }

        }

        stream.expect(Type.EXECUTE_END);

        return new IncludeNode(lineNumber, includeExpression, mapExpression);
    }

    @Override
    public String getTag() {
        return "include";
    }
}
