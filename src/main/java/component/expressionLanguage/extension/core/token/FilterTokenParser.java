package component.expressionLanguage.extension.core.token;

import component.expressionLanguage.expression.Expression;
import component.expressionLanguage.extension.core.expression.FilterExpression;
import component.expressionLanguage.extension.core.expression.RenderableNodeExpression;
import component.expressionLanguage.model.position.Position;
import component.expressionLanguage.model.tree.BodyNode;
import component.expressionLanguage.model.tree.Node;
import component.expressionLanguage.model.tree.PrintNode;
import component.expressionLanguage.token.parser.Parser;
import component.expressionLanguage.token.Token;
import component.expressionLanguage.token.TokenStream;
import component.expressionLanguage.token.Type;
import component.expressionLanguage.token.parser.TokenParser;
import component.expressionLanguage.token.parser.TokenStreamParser;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses the "filter" tag. It has nothing to do with implementing normal
 * filters.
 */
public class FilterTokenParser implements TokenParser {

    @Override
    public Node parse(Token token, TokenStreamParser parser) {
        TokenStream stream = parser.getStream();
        Position position = token.getPosition();

        // skip the 'filter' token
        stream.next();

        List<Expression<?>> filterInvocationExpressions = new ArrayList<>();

        filterInvocationExpressions.add(parser.getExpressionParser().parseFilterInvocationExpression());

        while(stream.current().isA(Type.OPERATOR, "|")){
            // skip the '|' token
            stream.next();
            filterInvocationExpressions.add(parser.getExpressionParser().parseFilterInvocationExpression());
        }

        stream.expect(Type.EXECUTE_END);

        BodyNode body = parser.subparse((Token subToken) -> subToken.isA(Type.NAME, "endfilter"));

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
