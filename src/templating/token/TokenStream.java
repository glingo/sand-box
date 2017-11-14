package templating.token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class TokenStream {

    private final ArrayList<Token> tokens = new ArrayList<>();
    private final String filename;
    private int current;

    /**
     * Constructor for a Token Stream
     *
     * @param tokens A collection of tokens
     * @param name The filename of the template that these tokens came from
     */
    public TokenStream(Collection<Token> tokens, String name) {
        this.tokens.addAll(tokens);
        this.current = 0;
        this.filename = name;
    }

    /**
     * Consumes and returns the next token in the stream.
     *
     * @return The next token
     */
    public Token next() {
        return tokens.get(++current);
    }

    /**
     * Checks the current token to see if it matches the provided type. If it
     * doesn't match this will throw a SyntaxException. This will consume a
     * token.
     *
     * @param type The type of token that we expect
     * @return Token The current token
     */
    public Token expect(String type) {
        return expect(type, null);
    }

    /**
     * Checks the current token to see if it matches the provided type. If it
     * doesn't match this will throw a SyntaxException. This will consume a
     * token.
     *
     * @param type The type of token that we expect
     * @param value The expected value of the token
     * @return Token The current token
     */
    public Token expect(String type, String value) {
        Token token = tokens.get(current);

        boolean success = Objects.isNull(value) 
                ? token.isA(type) : token.isA(type, value);

        if (!success) {
            String message = String.format("Unexpected token of value \"%s\" and type %s, expected token of type %s",
                    token.getValue(), token.getType().toString(), type);
            throw new IllegalStateException(message);
        }
        this.next();
        return token;
    }

    /**
     * Returns the next token in the stream without consuming it.
     *
     * @return The next token
     */
    public Token peek() {
        return peek(1);
    }

    /**
     * Returns a future token in the stream without consuming any.
     *
     * @param number How many tokens to lookahead
     * @return The token we are peeking at
     */
    public Token peek(int number) {
        return this.tokens.get(this.current + number);
    }

    @Override
    public String toString() {
        return tokens.toString();
    }

    /**
     * Looks at the current token. Does not consume the token.
     *
     * @return Token The current token
     */
    public Token current() {
        return this.tokens.get(current);
    }

    public String getFilename() {
        return filename;
    }

    /**
     * used for testing purposes
     *
     * @return List of tokens
     */
    public ArrayList<Token> getTokens() {
        return tokens;
    }
}
