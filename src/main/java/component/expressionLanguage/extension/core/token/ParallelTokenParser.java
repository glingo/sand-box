package component.expressionLanguage.extension.core.token;

import component.expressionLanguage.model.position.Position;
import component.expressionLanguage.model.tree.BodyNode;
import component.expressionLanguage.model.tree.Node;
import component.expressionLanguage.model.tree.ParallelNode;
import component.expressionLanguage.token.parser.Parser;
import component.expressionLanguage.token.Token;
import component.expressionLanguage.token.TokenStream;
import component.expressionLanguage.token.Type;
import component.expressionLanguage.token.parser.TokenParser;
import component.expressionLanguage.token.parser.TokenStreamParser;
import java.util.function.Predicate;

public class ParallelTokenParser implements TokenParser {

    private final Predicate<Token> decideParallelEnd = (Token token) -> token.isA(Type.NAME, "endparallel");

    @Override
    public Node parse(Token token, TokenStreamParser parser) {
        TokenStream stream = parser.getStream();
        Position position = token.getPosition();

        // skip the 'parallel' token
        stream.next();

        stream.expect(Type.EXECUTE_END);

        BodyNode body = parser.subparse(decideParallelEnd);

        // skip the 'endparallel' token
        stream.next();

        stream.expect(Type.EXECUTE_END);
        return new ParallelNode(position, body);
    }

    @Override
    public String getTag() {
        return "parallel";
    }
}
