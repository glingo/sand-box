package expressionLanguage.extension.core.token;

import expressionLanguage.model.tree.Node;
import expressionLanguage.parser.Parser;
import expressionLanguage.token.Token;
import expressionLanguage.token.parser.TokenParser;

/**
 * This is just a dummy class to point developers into the right direction; the
 * verbatim tag had to be implemented directly into the lexer.
 *
 */
public class VerbatimTokenParser implements TokenParser {

    @Override
    public Node parse(Token token, Parser parser) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getTag() {
        return "verbatim";
    }
}
