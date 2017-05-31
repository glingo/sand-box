package templating.tokenParser;

import templating.parser.Parser;
import templating.parser.StoppingCondition;
import templating.token.Token;
import templating.token.TokenStream;
import templating.token.Type;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import templating.expression.Expression;
import templating.node.BodyNode;
import templating.node.IfNode;
import templating.node.RenderableNode;

public class IfTokenParser extends AbstractTokenParser {
    
    private final StoppingCondition decideIfFork = (Token token) -> token.test(Type.NAME, "elseif", "else", "endif");

    private final StoppingCondition decideIfEnd = (Token token) -> token.test(Type.NAME, "endif");

    @Override
    public RenderableNode parse(Token token, Parser parser) throws Exception {
        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

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
                String msg = String.format("Unexpected end of template. Pebble was looking for the following tags \"else\", \"elseif\", or \"endif\" at line %s in file %s.", stream.current().getLineNumber(), stream.getFilename());
                throw new Exception(msg);
            }
        }

        stream.expect(Type.EXECUTE_END);
        return new IfNode(lineNumber, conditionsWithBodies, elseBody);
    }

    @Override
    public String getTag() {
        return "if";
    }
}
