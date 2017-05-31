package expressionLanguage.extension.core.token;

import expressionLanguage.model.position.Position;
import expressionLanguage.model.tree.BodyNode;
import expressionLanguage.model.tree.Node;
import expressionLanguage.model.tree.ParallelNode;
import expressionLanguage.parser.Parser;
import expressionLanguage.parser.StoppingCondition;
import expressionLanguage.token.Token;
import expressionLanguage.token.TokenStream;
import expressionLanguage.token.Type;
import expressionLanguage.token.parser.TokenParser;

public class ParallelTokenParser implements TokenParser {

    private final StoppingCondition decideParallelEnd = (Token token) -> token.test(Type.NAME, "endparallel");

    @Override
    public Node parse(Token token, Parser parser) throws Exception {
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
