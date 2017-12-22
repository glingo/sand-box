package component.expressionLanguage.extension.core.token;

import component.expressionLanguage.expression.Expression;
import component.expressionLanguage.model.position.Position;
import component.expressionLanguage.model.tree.ImportNode;
import component.expressionLanguage.model.tree.Node;
import component.expressionLanguage.token.parser.Parser;
import component.expressionLanguage.token.Token;
import component.expressionLanguage.token.TokenStream;
import component.expressionLanguage.token.Type;
import component.expressionLanguage.token.parser.TokenParser;
import component.expressionLanguage.token.parser.TokenStreamParser;

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
