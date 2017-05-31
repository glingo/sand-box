package expressionLanguage.extension.core.token;

import expressionLanguage.expression.Expression;
import expressionLanguage.extension.core.expression.MapExpression;
import expressionLanguage.model.position.Position;
import expressionLanguage.model.tree.IncludeNode;
import expressionLanguage.model.tree.Node;
import expressionLanguage.parser.Parser;
import expressionLanguage.token.Token;
import expressionLanguage.token.TokenStream;
import expressionLanguage.token.Type;
import expressionLanguage.token.parser.TokenParser;

public class IncludeTokenParser implements TokenParser {

    @Override
    public Node parse(Token token, Parser parser) throws Exception {

        TokenStream stream = parser.getStream();
        Position position = token.getPosition();

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
                String msg = String.format("Unexpected expression '%1s' at line %s in file %s.", parsedExpression .getClass().getCanonicalName(), token.getPosition(), stream.getFilename());
                throw new Exception(msg);
            }

        }

        stream.expect(Type.EXECUTE_END);

        return new IncludeNode(position, includeExpression, mapExpression);
    }

    @Override
    public String getTag() {
        return "include";
    }
}
