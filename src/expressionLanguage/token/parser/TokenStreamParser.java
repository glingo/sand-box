package expressionLanguage.token.parser;

import expressionLanguage.expression.Expression;
import expressionLanguage.expression.parser.ExpressionParser;
import expressionLanguage.model.tree.BodyNode;
import expressionLanguage.model.tree.Node;
import expressionLanguage.model.tree.PrintNode;
import expressionLanguage.model.tree.RootNode;
import expressionLanguage.model.tree.TextNode;
import expressionLanguage.operator.Operator;
import expressionLanguage.token.Token;
import expressionLanguage.token.TokenStream;
import expressionLanguage.token.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class TokenStreamParser implements Parser<TokenStream, RootNode> {

    /**
     * operators
     */
    private final Map<String, Operator> operators;

    /**
     * Token parsers
     */
    private final Map<String, TokenParser> tokenParsers;

    /**
     * An expression parser.
     */
    private ExpressionParser expressionParser;

    /**
     * The TokenStream that we are converting into an Abstract Syntax Tree.
     */
    private TokenStream stream;

    /**
     * TokenParser objects provided by the extensions. Used to keep track of the
     * name of the block that we are currently inside of. This is purely just
     * for the parent() function.
     */
    private LinkedList<String> blockStack;

    /**
     * Constructor
     *
     * @param operators A map of operators
     * @param tokenParsers A map of token parsers
     */
    public TokenStreamParser(Map<String, Operator> operators, Map<String, TokenParser> tokenParsers) {
        this.operators = operators;
        this.tokenParsers = tokenParsers;
    }

    @Override
    public RootNode parse(TokenStream stream) {

        // expression parser
        this.expressionParser = new ExpressionParser(this, this.operators);

        this.stream = stream;
        this.blockStack = new LinkedList<>();
        
        BodyNode body = subparse(Token::isEOF);

//        stop != null && stop.test(stream)
        RootNode root = new RootNode(stream.current().getPosition(), body);
        return root;
    }

    /**
     * The main method for the parser. This method does the work of converting a
     * TokenStream into a Node
     *
     * @param stop A stopping condition provided by a token parser
     * @return Node The root node of the generated Abstract Syntax Tree
     */
    public BodyNode subparse(Predicate<Token> stop) {
        // these nodes will be the children of the root node
        List<Node> nodes = new ArrayList<>();

        Token token = stream.current();
        while (!token.isEOF()) {

            switch (token.getType()) {
                case TEXT:

                    /*
                 * The current token is a text token. Not much to do here other
                 * than convert it to a text Node.
                     */
                    token = stream.current();
                    nodes.add(new TextNode(token.getPosition(), token.getValue()));
                    stream.next();
                    break;

                case PRINT_START:

                    /*
                 * We are entering a print delimited region at this point. These
                 * regions will contain some sort of expression so let's pass
                 * control to our expression parser.
                     */
                    // go to the next token because the current one is just the
                    // opening delimiter
                    token = stream.next();

                    Expression<?> expression = this.expressionParser.parseExpression();
                    nodes.add(new PrintNode(token.getPosition(), expression));

                    // we expect to see a print closing delimiter
                    stream.expect(Type.PRINT_END);

                    break;

                case EXECUTE_START:

                    // go to the next token because the current one is just the
                    // opening delimiter
                    stream.next();

                    token = stream.current();

                    /*
                    * We expect a name token at the beginning of every block.
                    *
                    * We do not use stream.expect() because it consumes the current
                    * token. The current token may be needed by a token parser
                    * which has provided a stopping condition. Ex. the 'if' token
                    * parser may need to check if the current token is either
                    * 'endif' or 'else' and act accordingly, thus we should not
                    * consume it.
                     */
                    if (!Type.NAME.equals(token.getType())) {
                        String msg = String.format("A block must start with a tag name at line %s in file %s.", token.getPosition(), stream.getFilename());
                        throw new IllegalStateException(msg);
                    }

                    // If this method was executed using a TokenParser and
                    // that parser provided a stopping condition (ex. checking
                    // for the 'endif' token) let's check for that condition
                    // now.
                    if (stop != null && stop.test(token)) {
                        return new BodyNode(token.getPosition(), nodes);
                    }

                    // find an appropriate parser for this name
                    TokenParser tokenParser = tokenParsers.get(token.getValue());

                    if (tokenParser == null) {
                        String msg = String.format("Unexpected tag name \"%s\" at %s", token.getValue(), token.getPosition());
                        throw new IllegalStateException(msg);
                    }

                    Node node = tokenParser.parse(token, this);

                    // node might be null (ex. "extend" token parser)
                    if (node != null) {
                        nodes.add(node);
                    }

                    break;

                default:
                    String msg = String.format("Parser ended in undefined state.", stream.current().getPosition());
                    throw new IllegalStateException(msg);
            }
        }
        return new BodyNode(stream.current().getPosition(), nodes);
    }

    public TokenStream getStream() {
        return stream;
    }

    public void setStream(TokenStream stream) {
        this.stream = stream;
    }

    public ExpressionParser getExpressionParser() {
        return this.expressionParser;
    }

    public String peekBlockStack() {
        return blockStack.peek();
    }

    public String popBlockStack() {
        return blockStack.pop();
    }

    public void pushBlockStack(String blockName) {
        blockStack.push(blockName);
    }

}
