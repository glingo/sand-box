package templating.tokenParser;

import templating.parser.Parser;
import templating.token.Token;
import templating.token.TokenStream;
import templating.token.Type;
import java.util.ArrayList;
import java.util.List;
import templating.expression.Expression;
import templating.expression.FilterExpression;
import templating.expression.RenderableNodeExpression;
import templating.node.BodyNode;
import templating.node.PrintNode;
import templating.node.RenderableNode;

/**
 * Parses the "filter" tag. It has nothing to do with implementing normal
 * filters.
 */
public class FilterTokenParser extends AbstractTokenParser {

    @Override
    public RenderableNode parse(Token token, Parser parser) throws Exception {
        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        // skip the 'filter' token
        stream.next();

        List<Expression<?>> filterInvocationExpressions = new ArrayList<>();

        filterInvocationExpressions.add(parser.getExpressionParser().parseFilterInvocationExpression());

        while(stream.current().test(Type.OPERATOR, "|")){
            // skip the '|' token
            stream.next();
            filterInvocationExpressions.add(parser.getExpressionParser().parseFilterInvocationExpression());
        }

        stream.expect(Type.EXECUTE_END);

        BodyNode body = parser.subparse((Token subToken) -> subToken.test(Type.NAME, "endfilter"));

        stream.next();
        stream.expect(Type.EXECUTE_END);

        Expression<?> lastExpression = new RenderableNodeExpression(body, stream.current().getLineNumber());

        for(Expression<?> filterInvocationExpression : filterInvocationExpressions){

            FilterExpression filterExpression = new FilterExpression();
            filterExpression.setRight(filterInvocationExpression);
            filterExpression.setLeft(lastExpression);

            lastExpression = filterExpression;
        }

        return new PrintNode(lastExpression, lineNumber);
    }

//    private StoppingCondition endFilter = (Token token) -> token.test(Token.Type.NAME, "endfilter");

    @Override
    public String getTag() {
        return "filter";
    }
}
