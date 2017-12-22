package component.expressionLanguage.lexer;

import component.util.StringUtils;
import component.expressionLanguage.model.position.Source;
import component.expressionLanguage.operator.Operator;
import component.expressionLanguage.token.Token;
import component.expressionLanguage.token.TokenStream;
import component.expressionLanguage.token.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import javafx.util.Pair;

public class Lexer {

    private static final Pattern PATTERN_NAME = compile("^[a-zA-Z_][a-zA-Z0-9_]*");

    private static final Pattern PATTERN_NUMBER = compile("^[0-9]+(\\.[0-9]+)?");

    // the negative lookbehind assertion is used to ignore escaped quotation marks
    private static final Pattern PATTERN_STRING = compile("((\").*?(?<!\\\\)(\"))|((').*?(?<!\\\\)('))", Pattern.DOTALL);

    private static final String PUNCTUATION = "()[]{}?:.,|=";

    protected Syntax syntax;

    protected Source source;

    protected ArrayList<Token> tokens;

    protected Pattern regexOperators;

    private LinkedList<Pair<String, Integer>> brackets;
    
    private boolean trimLeadingWhitespaceFromNextData = false;

    /**
     * The state of the lexer is important so that we know what to expect next
     * and to help discover errors in the template (ex. unclosed comments).
     */
    private State state;

    private LinkedList<State> states;

    private enum State {
        DATA, EXECUTE, PRINT, COMMENT
    }


    public Lexer() {
        
    }
    
    public TokenStream tokenize(Source source) {
        this.tokens = new ArrayList();
        this.source = source;
        
        return doTokenize();
    }

    public TokenStream doTokenize() {

        this.brackets = new LinkedList<>();

        this.states = new LinkedList<>();
        this.state = State.DATA;
        /*
         * loop through the entire source and apply different lexing methods
         * depending on what kind of state we are in at the time.
         *
         * This will always start on lexData();
         */
        while (this.source.length() > 0) {
            switch (this.state) {
                case DATA:
                    lexData();
                    break;
                case EXECUTE:
                    lexExecute();
                    break;
                case PRINT:
                    lexPrint();
                    break;
                case COMMENT:
                    lexComment();
                    break;
                default:
                    break;
            }

        }

        // end of file token
        pushToken(Type.EOF);

        // make sure that all brackets have been closed, else throw an error
        if (!this.brackets.isEmpty()) {
            String msg = String.format("Unclosed \"%s\" at .", brackets.pop().getKey(), source.getPosition());
            throw new IllegalStateException(msg);
        }

        return new TokenStream(tokens, source.getName());
    }

    private void lexData() {

        Matcher matcher = this.syntax.getRegexStartDelimiters().matcher(source);
        boolean match = matcher.find();

        String text;
        String startDelimiterToken = null;

        if (!match) {

            text = source.toString();
            source.advance(source.length());

        } else {

            text = source.substring(matcher.start());
            startDelimiterToken = source.substring(matcher.start(), matcher.end());
            source.advance(matcher.end());
        }
        
        if (trimLeadingWhitespaceFromNextData) {
            text = StringUtils.trimLeadingWhitespace(text);
            trimLeadingWhitespaceFromNextData = false;
        }
        
        Token textToken = pushToken(Type.TEXT, text);

        if (match) {

            checkForLeadingWhitespaceTrim(textToken);

            if (this.syntax.getCommentOpen().equals(startDelimiterToken)) {
                pushState(State.COMMENT);
            } else if (this.syntax.getPrintOpen().equals(startDelimiterToken)) {

                pushToken(Type.PRINT_START, this.syntax.getPrintOpen());
                pushState(State.PRINT);

            } else if ((this.syntax.getExecuteOpen().equals(startDelimiterToken))) {
                pushToken(Type.EXECUTE_START, this.syntax.getExecuteOpen());
                pushState(State.EXECUTE);
            }
        }

    }

    private void lexExecute() {

        // check for the trailing whitespace trim character
        checkForTrailingWhitespaceTrim();

        Matcher matcher = this.syntax.getRegexExecuteClose().matcher(source);

        // check if we are at the execute closing delimiter
        if (matcher.lookingAt() && brackets.isEmpty()) {
            pushToken(Type.EXECUTE_END, this.syntax.getExecuteClose());
            source.advance(matcher.end());
            popState();
        } else {
            lexExpression();
        }

    }

    private void lexPrint() {

        // check for the trailing whitespace trim character
        checkForTrailingWhitespaceTrim();
        Matcher matcher = this.syntax.getRegexPrintClose().matcher(source);

        // check if we are at the print closing delimiter
        if (matcher.lookingAt() && brackets.isEmpty()) {
            pushToken(Type.PRINT_END, this.syntax.getPrintClose());
            source.advance(matcher.end());
            popState();
        } else {
            lexExpression();
        }

    }

    private void lexExpression() {
        String token;

        // whitespace
        source.advanceThroughWhitespace();

        Matcher matcher;

        /*
         * Matcher matcher = REGEX_WHITESPACE.matcher(source); if
         * (matcher.lookingAt()) { source.advance(matcher.end()); }
         */
        // operators
        if (regexOperators != null) {
            matcher = regexOperators.matcher(source);
            if (matcher.lookingAt()) {
                token = source.substring(matcher.end());
                pushToken(Type.OPERATOR, token);
                source.advance(matcher.end());
                return;
            }
        }

        // names
        matcher = PATTERN_NAME.matcher(source);
        if (matcher.lookingAt()) {
            token = source.substring(matcher.end());
            pushToken(Type.NAME, token);
            source.advance(matcher.end());
            return;
        }

        // numbers
        matcher = PATTERN_NUMBER.matcher(source);
        if (matcher.lookingAt()) {
            token = source.substring(matcher.end());
            pushToken(Type.NUMBER, token);
            source.advance(matcher.end());
            return;
        }

        // punctuation
        if (PUNCTUATION.indexOf(source.charAt(0)) >= 0) {
            String character = String.valueOf(source.charAt(0));

            // opening bracket
            if ("([{".contains(character)) {
                brackets.push(new Pair<>(character, source.getLineNumber()));
            } // closing bracket
            else if (")]}".contains(character)) {
                if (brackets.isEmpty()) {
                    String msg = String.format("Unexpected \"" + character + "\" at %s", source.getPosition());
                    throw new IllegalStateException(msg);
                } else {
                    HashMap<String, String> validPairs = new HashMap<>();
                    validPairs.put("(", ")");
                    validPairs.put("[", "]");
                    validPairs.put("{", "}");
                    String lastBracket = brackets.pop().getKey();
                    String expected = validPairs.get(lastBracket);
                    if (!expected.equals(character)) {
                        String msg = String.format("Unclosed \"" + expected + "\" at %s", source.getPosition());
                        throw new IllegalStateException(msg);
                    }
                }
            }

            pushToken(Type.PUNCTUATION, character);
            source.advance(1);
            return;
        }

        // strings
        matcher = PATTERN_STRING.matcher(source);
        if (matcher.lookingAt()) {
            token = source.substring(matcher.end());

            source.advance(matcher.end());

            char quotationType = token.charAt(0);

            // remove first and last quotation marks
            token = token.substring(1, token.length() - 1);

            // remove backslashes used to escape inner quotation marks
            if (quotationType == '\'') {
                token = token.replaceAll("\\\\(')", "$1");
            } else if (quotationType == '"') {
                token = token.replaceAll("\\\\(\")", "$1");
            }

            pushToken(Type.STRING, token);
            return;
        }

        String msg = String.format("Unexpected character [%s], at %s.", source.charAt(0), source.getPosition());
        // we should have found something and returned by this point
        throw new IllegalStateException(msg);
    }

    private void lexComment() {

        // all we need to do is find the end of the comment.
        Matcher matcher = this.syntax.getRegexCommentClose().matcher(source);

        boolean match = matcher.find(0);

        if (!match) {
            String msg = String.format("Unclosed comment at line %s, in file %s.", source.getLineNumber(), source.getName());
            throw new IllegalStateException(msg);
        }

        /*
         * check if the commented ended with the whitespace trim character by
         * reversing the comment and performing a regular forward regex search.
         */
        String comment = source.substring(matcher.start());
        String reversedComment = new StringBuilder(comment).reverse().toString();
        Matcher whitespaceTrimMatcher = this.syntax.getRegexLeadingWhitespaceTrim().matcher(reversedComment);

        if (whitespaceTrimMatcher.lookingAt()) {
            this.trimLeadingWhitespaceFromNextData = true;
        }

        // move cursor to end of comment (and closing delimiter)
        source.advance(matcher.end());
        popState();
    }

    private void checkForTrailingWhitespaceTrim() {
        Matcher whitespaceTrimMatcher = this.syntax.getRegexTrailingWhitespaceTrim().matcher(source);
        
        if(whitespaceTrimMatcher.lookingAt()) {
            this.trimLeadingWhitespaceFromNextData = true;
        }
    }

    private void checkForLeadingWhitespaceTrim(Token leadingToken) {

        Matcher whitespaceTrimMatcher = this.syntax.getRegexLeadingWhitespaceTrim().matcher(source);

        if (whitespaceTrimMatcher.lookingAt()) {
            if (leadingToken != null) {
                leadingToken.setValue(StringUtils.trimLeadingWhitespace(leadingToken.getValue()));
            }
            source.advance(whitespaceTrimMatcher.end());
        }
    }

    private Token pushToken(Type type) {
        return pushToken(type, null);
    }

    /**
     * Create a Token of a certain type and value and push it into the list of
     * tokens that we are maintaining. `
     *
     * @param type The type of token we are creating
     * @param value The value of the new token
     */
    private Token pushToken(Type type, String value) {

        // ignore empty text tokens
        if (type.equals(Type.TEXT) && (value == null || "".equals(value))) {
            return null;
        }
//source.getName(), source.getLineNumber(), 0
        Token result = new Token(type, value, this.source.getPosition());

        this.tokens.add(result);

        return result;
    }

//    /**
//     * Retrieves the operators (both unary and binary) from the PebbleEngine and
//     * then dynamically creates one giant regular expression to detect for the
//     * existence of one of these operators.
//     *
//     * @return Pattern The regular expression used to find an operator
//     */
//    private void buildOperatorRegex() {
//
//        List<String> operators = new ArrayList<>();
//
//        if (unaryOperators != null) {
//            unaryOperators.keySet().stream().forEach(operators::add);
//        }
//
//        if (binaryOperators != null) {
//            binaryOperators.keySet().stream().forEach(operators::add);
//        }
//
//        if (!operators.isEmpty()) {
//            StringBuilder regex = new StringBuilder("^");
//
//            boolean isFirst = true;
//
//            for (String operator : operators) {
//                if (isFirst) {
//                    isFirst = false;
//                } else {
//                    regex.append("|");
//                }
//                regex.append(Pattern.quote(operator));
//
//                char nextChar = operator.charAt(operator.length() - 1);
//                if (Character.isLetter(nextChar) || Character.getType(nextChar) == Character.LETTER_NUMBER) {
//                    regex.append("(?![a-zA-Z])");
//                }
//            }
//
//            this.regexOperators = Pattern.compile(regex.toString());
//        }
//
//    }

    /**
     * Pushes the current state onto the stack and then updates the current
     * state to the new state.
     *
     * @param state The new state to use as the current state
     */
    private void pushState(State state) {
        this.states.push(this.state);
        this.state = state;
    }

    /**
     * Pop state from the stack
     */
    private void popState() {
        this.state = this.states.pop();
    }

    public Syntax getSyntax() {
        return syntax;
    }

    public void setSyntax(Syntax syntax) {
        this.syntax = syntax;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setRegexOperators(Pattern regexOperators) {
        this.regexOperators = regexOperators;
    }

    public void setTrimLeadingWhitespaceFromNextData(boolean trimLeadingWhitespaceFromNextData) {
        this.trimLeadingWhitespaceFromNextData = trimLeadingWhitespaceFromNextData;
    }
    
    public static LexerBuilder builder() {
        return new LexerBuilder();
    }
    
    public static class LexerBuilder {
        
        protected Syntax syntax;

        protected Map<String, Operator> operators;

        private boolean trimLeadingWhitespaceFromNextData = false;
        
        public LexerBuilder syntax(Syntax syntax) {
            this.syntax = syntax;
            return this;
        }
        
        public LexerBuilder operator(String symbol, Operator operator) {
            if (this.operators == null) {
                this.operators = new HashMap<>();
            }
            
            this.operators.put(symbol, operator);
            return this;
        }
        
        public LexerBuilder operators(Map<String, Operator> operators) {
            if (this.operators == null) {
                this.operators = new HashMap<>();
            }
            
            this.operators.putAll(operators);
            return this;
        }
        
        public LexerBuilder trim(boolean trim) {
            this.trimLeadingWhitespaceFromNextData = trim;
            return this;
        }
        
        
        public Lexer build() {
            Lexer lexer = new Lexer();
            
            lexer.setSyntax(syntax);
            lexer.setTrimLeadingWhitespaceFromNextData(trimLeadingWhitespaceFromNextData);
            
            if (this.operators != null && !this.operators.isEmpty()) {
                String regex = this.operators.keySet().stream().reduce("^", (a, symbol) -> {
                    char nextChar = symbol.charAt(symbol.length() - 1);
                    return a.concat(a.contentEquals("^") ? "" : "|")
                            .concat(Pattern.quote(symbol))
                            .concat(Character.isLetter(nextChar) || Character.getType(nextChar) == Character.LETTER_NUMBER ? "(?![a-zA-Z])" : "");
                });

                lexer.setRegexOperators(Pattern.compile(regex));
            }
            
            return lexer;
        }
        
    }
}
