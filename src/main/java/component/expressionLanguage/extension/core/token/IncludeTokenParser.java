package component.expressionLanguage.extension.core.token;

import component.expressionLanguage.expression.Expression;
import component.expressionLanguage.extension.core.expression.MapExpression;
import component.expressionLanguage.model.position.Position;
import component.expressionLanguage.model.tree.IncludeNode;
import component.expressionLanguage.model.tree.Node;
import component.expressionLanguage.token.Token;
import component.expressionLanguage.token.TokenStream;
import component.expressionLanguage.token.Type;
import component.expressionLanguage.token.parser.TokenParser;
import component.expressionLanguage.token.parser.TokenStreamParser;

public class IncludeTokenParser implements TokenParser {

    @Override
    public Node parse(Token token, TokenStreamParser parser) {

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
                throw new IllegalStateException(msg);
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
