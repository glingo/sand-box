package templating.tokenParser;

import templating.node.RenderableNode;
import templating.parser.Parser;
import templating.token.Token;

/**
 * This is just a dummy class to point developers into the right direction; the
 * verbatim tag had to be implemented directly into the lexer.
 *
 */
public class VerbatimTokenParser extends AbstractTokenParser {

    @Override
    public RenderableNode parse(Token token, Parser parser) throws Exception {

        throw new UnsupportedOperationException();
    }

    @Override
    public String getTag() {
        return "verbatim";
    }
}
