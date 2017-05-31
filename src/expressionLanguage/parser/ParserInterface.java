package expressionLanguage.parser;

import expressionLanguage.expression.parser.ExpressionParser;
import expressionLanguage.model.tree.BodyNode;
import expressionLanguage.model.tree.RootNode;
import expressionLanguage.token.TokenStream;

public interface ParserInterface {

    RootNode parse(TokenStream stream) throws Exception;

    BodyNode subparse() throws Exception;

    /**
     * Provides the stream of tokens which ultimately need to be "parsed" into
     * Nodes.
     *
     * @return TokenStream
     */
    TokenStream getStream();

    /**
     * Parses the existing TokenStream, starting at the current Token, and
     * ending when the stopCondition is fullfilled.
     *
     * @param stopCondition The condition to stop parsing a segment of the template.
     * @return A node representing the parsed section
     * @throws Exception Thrown if an error occurs.
     */
    BodyNode subparse(StoppingCondition stopCondition) throws Exception;

    ExpressionParser getExpressionParser();

    String peekBlockStack();

    String popBlockStack();

    void pushBlockStack(String blockName);

}
