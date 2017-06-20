package expressionLanguage.extension.core.token;

import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;
import expressionLanguage.model.tree.Node;
import expressionLanguage.model.tree.SetNode;
import expressionLanguage.token.parser.Parser;
import expressionLanguage.token.Token;
import expressionLanguage.token.TokenStream;
import expressionLanguage.token.Type;
import expressionLanguage.token.parser.TokenParser;
import expressionLanguage.token.parser.TokenStreamParser;

public class SetTokenParser implements TokenParser {

    @Override
    public Node parse(Token token, TokenStreamParser parser) {
        TokenStream stream = parser.getStream();
        Position position = token.getPosition();

        // skip the 'set' token
        stream.next();

        String name = parser.getExpressionParser().parseNewVariableName();

        stream.expect(Type.PUNCTUATION, "=");

        Expression<?> value = parser.getExpressionParser().parseExpression();

        stream.expect(Type.EXECUTE_END);

        return new SetNode(position, name, value);
    }

    @Override
    public String getTag() {
        return "set";
    }
}
