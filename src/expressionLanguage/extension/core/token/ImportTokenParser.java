package expressionLanguage.extension.core.token;

import expressionLanguage.expression.Expression;
import expressionLanguage.model.position.Position;
import expressionLanguage.model.tree.ImportNode;
import expressionLanguage.model.tree.Node;
import expressionLanguage.token.parser.Parser;
import expressionLanguage.token.Token;
import expressionLanguage.token.TokenStream;
import expressionLanguage.token.Type;
import expressionLanguage.token.parser.TokenParser;
import expressionLanguage.token.parser.TokenStreamParser;

public class ImportTokenParser implements TokenParser {

    @Override
    public Node parse(Token token, TokenStreamParser parser) {

        TokenStream stream = parser.getStream();
        Position position = token.getPosition();

        // skip over the 'import' token
        stream.next();

        Expression<?> importExpression = parser.getExpressionParser().parseExpression();

        stream.expect(Type.EXECUTE_END);

        return new ImportNode(position, importExpression);
    }

    @Override
    public String getTag() {
        return "import";
    }
}
