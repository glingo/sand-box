package templating.tokenParser;

import templating.expression.Expression;
import templating.node.ImportNode;
import templating.node.RenderableNode;
import templating.parser.Parser;
import templating.token.Token;
import templating.token.TokenStream;
import templating.token.Type;


public class ImportTokenParser extends AbstractTokenParser {

    @Override
    public RenderableNode parse(Token token, Parser parser) throws Exception {

        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        // skip over the 'import' token
        stream.next();

        Expression<?> importExpression = parser.getExpressionParser().parseExpression();

        stream.expect(Type.EXECUTE_END);

        return new ImportNode(lineNumber, importExpression);
    }

    @Override
    public String getTag() {
        return "import";
    }
}
