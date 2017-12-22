package component.expressionLanguage.extension.core.token;

import component.expressionLanguage.expression.Expression;
import component.expressionLanguage.model.position.Position;
import component.expressionLanguage.model.tree.Node;
import component.expressionLanguage.model.tree.SetNode;
import component.expressionLanguage.token.parser.Parser;
import component.expressionLanguage.token.Token;
import component.expressionLanguage.token.TokenStream;
import component.expressionLanguage.token.Type;
import component.expressionLanguage.token.parser.TokenParser;
import component.expressionLanguage.token.parser.TokenStreamParser;

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
