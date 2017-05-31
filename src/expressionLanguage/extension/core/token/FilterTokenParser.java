package expressionLanguage.extension.core.token;

import expressionLanguage.expression.Expression;
import expressionLanguage.extension.core.expression.FilterExpression;
import expressionLanguage.extension.core.expression.RenderableNodeExpression;
import expressionLanguage.model.position.Position;
import expressionLanguage.model.tree.BodyNode;
import expressionLanguage.model.tree.Node;
import expressionLanguage.model.tree.PrintNode;
import expressionLanguage.parser.Parser;
import expressionLanguage.token.Token;
import expressionLanguage.token.TokenStream;
import expressionLanguage.token.Type;
import expressionLanguage.token.parser.TokenParser;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses the "filter" tag. It has nothing to do with implementing normal
 * filters.
 */
public class FilterTokenParser implements TokenParser {

    @Override
    public Node parse(Token token, Parser parser) throws Exception {
        TokenStream stream = parser.getStream();
        Position position = token.getPosition();

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

        Expression<?> lastExpression = new RenderableNodeExpression(body);

        for(Expression<?> filterInvocationExpression : filterInvocationExpressions){

            FilterExpression filterExpression = new FilterExpression();
            filterExpression.setRightExpression(filterInvocationExpression);
            filterExpression.setLeftExpression(lastExpression);

            lastExpression = filterExpression;
        }

        return new PrintNode(position, lastExpression);
    }

//    private StoppingCondition endFilter = (Token token) -> token.test(Token.Type.NAME, "endfilter");

    @Override
    public String getTag() {
        return "filter";
    }
}
