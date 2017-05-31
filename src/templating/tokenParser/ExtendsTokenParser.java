package templating.tokenParser;

import templating.expression.Expression;
import templating.node.ExtendsNode;
import templating.node.RenderableNode;
import templating.parser.Parser;
import templating.token.Token;
import templating.token.TokenStream;
import templating.token.Type;

public class ExtendsTokenParser extends AbstractTokenParser {

    @Override
    public RenderableNode parse(Token token, Parser parser) throws Exception {
        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        // skip the 'extends' token
        stream.next();

        Expression<?> parentTemplateExpression = parser.getExpressionParser().parseExpression();

        stream.expect(Type.EXECUTE_END);
        return new ExtendsNode(lineNumber, parentTemplateExpression);
    }

    @Override
    public String getTag() {
        return "extends";
    }
}
