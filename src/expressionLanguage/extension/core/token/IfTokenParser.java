package expressionLanguage.extension.core.token;

import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;
import expressionLanguage.model.tree.BodyNode;
import expressionLanguage.model.tree.IfNode;
import expressionLanguage.model.tree.Node;
import expressionLanguage.token.parser.Parser;
import expressionLanguage.token.Token;
import expressionLanguage.token.TokenStream;
import expressionLanguage.token.Type;
import expressionLanguage.token.parser.TokenParser;
import expressionLanguage.token.parser.TokenStreamParser;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javafx.util.Pair;

public class IfTokenParser implements TokenParser {
    
    private final Predicate<Token> decideIfFork = (Token token) -> token.isA(Type.NAME, "elseif", "else", "endif");

    private final Predicate<Token> decideIfEnd = (Token token) -> token.isA(Type.NAME, "endif");

    @Override
    public Node parse(Token token, TokenStreamParser parser) {
        TokenStream stream = parser.getStream();
        Position position = token.getPosition();

        // skip the 'if' token
        stream.next();

        List<Pair<Expression<?>, BodyNode>> conditionsWithBodies = new ArrayList<>();

        Expression<?> expression = parser.getExpressionParser().parseExpression();

        stream.expect(Type.EXECUTE_END);

        BodyNode body = parser.subparse(decideIfFork);

        conditionsWithBodies.add(new Pair<>(expression, body));

        BodyNode elseBody = null;
        boolean end = false;
        while (!end) {
            switch (stream.current().getValue()) {
            case "else":
                stream.next();
                stream.expect(Type.EXECUTE_END);
                elseBody = parser.subparse(decideIfEnd);
                break;

            case "elseif":
                stream.next();
                expression = parser.getExpressionParser().parseExpression();
                stream.expect(Type.EXECUTE_END);
                body = parser.subparse(decideIfFork);
                conditionsWithBodies.add(new Pair<>(expression, body));
                break;

            case "endif":
                stream.next();
                end = true;
                break;
            default:
                String msg = String.format("Unexpected end of template. Pebble was looking for the following tags \"else\", \"elseif\", or \"endif\" at line %s in file %s.", stream.current().getPosition(), stream.getFilename());
                throw new IllegalStateException(msg);
            }
        }

        stream.expect(Type.EXECUTE_END);
        return new IfNode(position, conditionsWithBodies, elseBody);
    }

    @Override
    public String getTag() {
        return "if";
    }
}
