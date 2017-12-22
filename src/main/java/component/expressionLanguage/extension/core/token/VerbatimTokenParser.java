package component.expressionLanguage.extension.core.token;

import component.expressionLanguage.model.tree.Node;
import component.expressionLanguage.token.Token;
import component.expressionLanguage.token.parser.TokenParser;
import component.expressionLanguage.token.parser.TokenStreamParser;

/**
 * This is just a dummy class to point developers into the right direction; the
 * verbatim tag had to be implemented directly into the lexer.
 *
 */
public class VerbatimTokenParser implements TokenParser {

    @Override
    public Node parse(Token token, TokenStreamParser parser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getTag() {
        return "verbatim";
    }
}
