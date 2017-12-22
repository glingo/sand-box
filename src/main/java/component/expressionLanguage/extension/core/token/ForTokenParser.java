package component.expressionLanguage.extension.core.token;

import component.expressionLanguage.expression.Expression;
import component.expressionLanguage.model.position.Position;
import component.expressionLanguage.model.tree.BodyNode;
import component.expressionLanguage.model.tree.ForNode;
import component.expressionLanguage.model.tree.Node;
import component.expressionLanguage.token.parser.Parser;
import component.expressionLanguage.token.Token;
import component.expressionLanguage.token.TokenStream;
import component.expressionLanguage.token.Type;
import component.expressionLanguage.token.parser.TokenParser;
import component.expressionLanguage.token.parser.TokenStreamParser;

public class ForTokenParser implements TokenParser {

    @Override
    public Node parse(Token token, TokenStreamParser parser) {
        TokenStream stream = parser.getStream();
        Position position = token.getPosition();

        // skip the 'for' token
        stream.next();

        // get the iteration variable
        String iterationVariable = parser.getExpressionParser().parseNewVariableName();

        stream.expect(Type.NAME, "in");

        // get the iterable variable
        Expression<?> iterable = parser.getExpressionParser().parseExpression();

        stream.expect(Type.EXECUTE_END);

        BodyNode body = parser.subparse((Token subToken) -> subToken.isA(Type.NAME, "else", "endfor"));

        BodyNode elseBody = null;

        if (stream.current().isA(Type.NAME, "else")) {
            // skip the 'else' token
            stream.next();
            stream.expect(Type.EXECUTE_END);
            elseBody = parser.subparse((Token subToken) -> subToken.isA(Type.NAME, "endfor"));
        }

        // skip the 'endfor' token
        stream.next();

        stream.expect(Type.EXECUTE_END);

        return new ForNode(position, iterationVariable, iterable, body, elseBody);
    }
    
    @Override
    public String getTag() {
        return "for";
    }
}
