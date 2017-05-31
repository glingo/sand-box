package templating.tokenParser;

import templating.node.BodyNode;
import templating.node.ParallelNode;
import templating.node.RenderableNode;
import templating.parser.Parser;
import templating.parser.StoppingCondition;
import templating.token.Token;
import templating.token.TokenStream;
import templating.token.Type;

public class ParallelTokenParser extends AbstractTokenParser {

    private final StoppingCondition decideParallelEnd = (Token token) -> token.test(Type.NAME, "endparallel");

    @Override
    public RenderableNode parse(Token token, Parser parser) throws Exception {
        TokenStream stream = parser.getStream();
        int lineNumber = token.getLineNumber();

        // skip the 'parallel' token
        stream.next();

        stream.expect(Type.EXECUTE_END);

        BodyNode body = parser.subparse(decideParallelEnd);

        // skip the 'endparallel' token
        stream.next();

        stream.expect(Type.EXECUTE_END);
        return new ParallelNode(lineNumber, body);
    }

    @Override
    public String getTag() {
        return "parallel";
    }
}
