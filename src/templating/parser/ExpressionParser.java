package templating.parser;

import templating.operator.*;
import templating.token.*;
import templating.expression.*;
import templating.node.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Parses expressions.
 */
public class ExpressionParser {

    private static final Set<String> RESERVED_KEYWORDS = new HashSet<>(Arrays.asList("true", "false", "null", "none"));

    private final Parser parser;

    private TokenStream stream;

    private final Map<String, BinaryOperator> binaryOperators;

    private final Map<String, UnaryOperator> unaryOperators;

    /**
     * Constructor
     *
     * @param parser A reference to the main parser
     * @param binaryOperators All the binary operators
     * @param unaryOperators All the unary operators
     */
    public ExpressionParser(Parser parser, Map<String, BinaryOperator> binaryOperators, Map<String, UnaryOperator> unaryOperators) {
        this.parser = parser;
        this.binaryOperators = binaryOperators;
        this.unaryOperators = unaryOperators;
    }

    /**
     * The public entry point for parsing an expression.
     *
     * @return NodeExpression the expression that has been parsed.
     * @throws Exception Thrown if a parsing error occurs
     */
    public Expression<?> parseExpression() throws Exception {
        return parseExpression(0);
    }

    /**
     * A private entry point for parsing an expression. This method takes in the
     * precedence required to operate a "precedence climbing" parsing algorithm.
     * It is a recursive method.
     *
     * @see http://en.wikipedia.org/wiki/Operator-precedence_parser
     *
     * @return The NodeExpression representing the parsed expression.
     * @throws ParserException Thrown if a parsing error occurs.
     */
    private Expression<?> parseExpression(int minPrecedence) throws Exception {

        this.stream = parser.getStream();
        Token token = stream.current();
        Expression<?> expression = null;

        /*
         * The first check is to see if the expression begins with a unary
         * operator, or an opening bracket, or neither.
         */
        if (isUnary(token)) {
            UnaryOperator operator = this.unaryOperators.get(token.getValue());
            stream.next();
            expression = parseExpression(operator.getPrecedence());

            UnaryExpression unaryExpression = null;
            Class<? extends Expression<?>> operatorExpression = operator.getExpression();
            try {
                unaryExpression = (UnaryExpression) operatorExpression.newInstance();
                unaryExpression.setLineNumber(stream.current().getLineNumber());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            unaryExpression.setChildExpression(expression);

            expression = unaryExpression;

        } else if (token.test(Type.PUNCTUATION, "(")) {

            stream.next();
            expression = parseExpression();
            stream.expect(Type.PUNCTUATION, ")");
            expression = parsePostfixExpression(expression);

        }  else if (token.test(Type.PUNCTUATION, "[")) { // array definition syntax

            // preserve [ token for array parsing
            expression = parseArrayDefinitionExpression();
            // don't expect ], because it has been already expected
            // currently, postfix expressions are not supported for arrays
            // expression = parsePostfixExpression(expression);
        }  else if (token.test(Type.PUNCTUATION, "{")) { // map definition syntax

            // preserve { token for map parsing
            expression = parseMapDefinitionExpression();
            // don't expect }, because it has been already expected
            // currently, postfix expressions are not supported for maps
            // expression = parsePostfixExpression(expression);

        } else {
            /*
             * starts with neither. Let's parse out the first expression that we
             * can find. There may be one, there may be many (separated by
             * binary operators); right now we are just looking for the first.
             */
            expression = subparseExpression();
        }

        /*
         * If, after parsing the first expression we encounter a binary operator
         * then we know we have another expression on the other side of the
         * operator that requires parsing. Otherwise we're done.
         */
        token = stream.current();
        while (isBinary(token) && binaryOperators.get(token.getValue()).getPrecedence() >= minPrecedence) {

            // find out which operator we are dealing with and then skip over it
            BinaryOperator operator = binaryOperators.get(token.getValue());
            stream.next();

            Expression<?> expressionRight = null;

            // the right hand expression of the FILTER operator is handled in a
            // unique way
            if (FilterExpression.class.equals(operator.getExpression())) {
                expressionRight = parseFilterInvocationExpression();
            }  else if (PositiveTestExpression.class.equals(operator.getExpression())
                    || NegativeTestExpression.class.equals(operator.getExpression())) {
                // the right hand expression of TEST operators is handled in a
                // unique way
                expressionRight = parseTestInvocationExpression();
            } else {
                /*
                 * parse the expression on the right hand side of the operator
                 * while maintaining proper associativity and precedence
                 */
                expressionRight = parseExpression(Associativity.LEFT.equals(operator.getAssociativity()) ? operator
                        .getPrecedence() + 1 : operator.getPrecedence());
            }

            /*
             * we have to wrap the left and right side expressions into one
             * final expression. The operator provides us with the type of
             * expression we are creating.
             */
            BinaryExpression<?> finalExpression = null;
            Class<? extends Expression<?>> operatorExpression = operator.getExpression();
            try {
                finalExpression = (BinaryExpression<?>) operatorExpression.newInstance();
                finalExpression.setLineNumber(stream.current().getLineNumber());
            } catch (InstantiationException | IllegalAccessException e) {
                String msg = String.format("Error instantiating operator node [%s] at line %s in file %s.", operatorExpression.getName(), token.getLineNumber(), stream.getFilename());
                throw new Exception(msg);
            }

            finalExpression.setLeft(expression);
            finalExpression.setRight(expressionRight);

            expression = finalExpression;

            token = stream.current();
        }

        if (minPrecedence == 0) {
            return parseTernaryExpression(expression);
        }

        return expression;
    }

    /**
     * Checks if a token is a unary operator.
     *
     * @param token The token that we are checking
     * @return boolean Whether the token is a unary operator or not
     */
    private boolean isUnary(Token token) {
        return token.test(Type.OPERATOR) && this.unaryOperators.containsKey(token.getValue());
    }

    /**
     * Checks if a token is a binary operator.
     *
     * @param token The token that we are checking
     * @return boolean Whether the token is a binary operator or not
     */
    private boolean isBinary(Token token) {
        return token.test(Type.OPERATOR) && this.binaryOperators.containsKey(token.getValue());
    }

    /**
     * Finds and returns the next "simple" expression; an expression of which
     * can be found on either side of a binary operator but does not contain a
     * binary operator. Ex. "var.field", "true", "12", etc.
     *
     * @return NodeExpression The expression that it found.
     * @throws Exception Thrown if a parsing error occurs.
     */
    private Expression<?> subparseExpression() throws Exception {
        final Token token = stream.current();
        Expression<?> node = null;

        switch (token.getType()) {

            case NAME:
                switch (token.getValue()) {

                    // a constant?
                    case "true":
                    case "TRUE":
                        node = new LiteralBooleanExpression(true, token.getLineNumber());
                        break;
                    case "false":
                    case "FALSE":
                        node = new LiteralBooleanExpression(false, token.getLineNumber());
                        break;
                    case "none":
                    case "NONE":
                    case "null":
                    case "NULL":
                        node = new LiteralNullExpression(token.getLineNumber());
                        break;

                    default:

                        // name of a function?
                        if (stream.peek().test(Type.PUNCTUATION, "(")) {
                            node = new FunctionOrMacroNameNode(token.getValue(), stream.peek().getLineNumber());
                        } // variable name
                        else {
                            node = new ContextVariableExpression(token.getValue(), token.getLineNumber());
                        }
                        break;
                }
                break;

            case NUMBER:
                final String numberValue = token.getValue();
                if (numberValue.contains(".")) {
                    node = new LiteralDoubleExpression(Double.valueOf(numberValue), token.getLineNumber());
                } else {
                    node = new LiteralLongExpression(Long.valueOf(numberValue), token.getLineNumber());
                }

                break;

            case STRING:
                node = new LiteralStringExpression(token.getValue(), token.getLineNumber());
                break;

            // not found, syntax error
            default:
                String msg = String.format("Unexpected token \"%s\" of value \"%s\" at line %s in file %s.", token.getType()
                        .toString(), token.getValue(), token.getLineNumber(), stream.getFilename());
                throw new Exception(msg);
        }

        // there may or may not be more to this expression - let's keep looking
        stream.next();
        return parsePostfixExpression(node);
    }

    private Expression<?> parseTernaryExpression(Expression<?> expression) throws Exception {
        // if the next token isn't a ?, we're not dealing with a ternary expression
        if (!stream.current().test(Type.PUNCTUATION, "?")) {
            return expression;
        }

        stream.next();
        Expression<?> expression2 = parseExpression();
        stream.expect(Type.PUNCTUATION, ":");
        Expression<?> expression3 = parseExpression();

        expression = new TernaryExpression((Expression<Boolean>) expression, expression2, expression3, this.stream
                .current().getLineNumber(), stream.getFilename());
        return expression;
    }

    /**
     * Determines if there is more to the provided expression than we originally
     * thought. We will look for the filter operator or perhaps we are getting
     * an attribute from a variable (ex. var.attribute or var['attribute'] or
     * var.attribute(bar)).
     *
     * @param node The expression that we have already discovered
     * @return Either the original expression that was passed in or a slightly
     * modified version of it, depending on what was discovered.
     * @throws Exception Thrown if a parsing error occurs.
     */
    private Expression<?> parsePostfixExpression(Expression<?> node) throws Exception {
        Token current;
        while (true) {
            current = stream.current();

            if (current.test(Type.PUNCTUATION, ".") || current.test(Type.PUNCTUATION, "[")) {

                // a period represents getting an attribute from a variable or
                // calling a method
                node = parseBeanAttributeExpression(node);

            } else if (current.test(Type.PUNCTUATION, "(")) {

                // function call
                node = parseFunctionOrMacroInvocation(node);

            } else {
                break;
            }
        }
        return node;
    }

    private Expression<?> parseFunctionOrMacroInvocation(Expression<?> node) throws Exception {
        String functionName = ((FunctionOrMacroNameNode) node).getName();
        ArgumentsNode args = parseArguments();

        /*
         * The following core functions have their own Nodes and are rendered in
         * unique ways for the sake of performance.
         */
        switch (functionName) {
            case "parent":
                return new ParentFunctionExpression(parser.peekBlockStack(), stream.current().getLineNumber());
            case "block":
                return new BlockFunctionExpression(args, node.getLineNumber());
        }

        return new FunctionOrMacroInvocationExpression(functionName, args, node.getLineNumber());
    }

    public FilterInvocationExpression parseFilterInvocationExpression() throws Exception {
        TokenStream stream = parser.getStream();
        Token filterToken = stream.expect(Type.NAME);

        ArgumentsNode args = null;
        if (stream.current().test(Type.PUNCTUATION, "(")) {
            args = this.parseArguments();
        } else {
            args = new ArgumentsNode(null, null, filterToken.getLineNumber());
        }

        return new FilterInvocationExpression(filterToken.getValue(), args, filterToken.getLineNumber());
    }

    private Expression<?> parseTestInvocationExpression() throws Exception {
        TokenStream stream = parser.getStream();
        int lineNumber = stream.current().getLineNumber();

        Token testToken = stream.expect(Type.NAME);

        ArgumentsNode args = null;
        if (stream.current().test(Type.PUNCTUATION, "(")) {
            args = this.parseArguments();
        } else {
            args = new ArgumentsNode(null, null, testToken.getLineNumber());
        }

        return new TestInvocationExpression(lineNumber, testToken.getValue(), args);
    }

    /**
     * A bean attribute expression can either be an expression getting an
     * attribute from a variable in the context, or calling a method from a
     * variable.
     *
     * Ex. foo.bar or foo['bar'] or foo.bar('baz')
     *
     * @param node The expression parsed so far
     * @return NodeExpression The parsed subscript expression
     * @throws Exception Thrown if a parsing error occurs.
     */
    private Expression<?> parseBeanAttributeExpression(Expression<?> node) throws Exception {
        TokenStream stream = parser.getStream();

        if (stream.current().test(Type.PUNCTUATION, ".")) {

            // skip over the '.' token
            stream.next();

            Token token = stream.expect(Type.NAME);

            ArgumentsNode args = null;
            if (stream.current().test(Type.PUNCTUATION, "(")) {
                args = this.parseArguments();
                if (!args.getNamedArgs().isEmpty()) {
                    String msg = String.format("Can not use named arguments when calling a bean method at line %s in file %s.", stream.current().getLineNumber(), stream.getFilename());
                    throw new Exception(msg);
                }
            }

            node = new GetAttributeExpression(node, new LiteralStringExpression(token.getValue(), token.getLineNumber()), args,
                    stream.getFilename(), token.getLineNumber());

        } else if (stream.current().test(Type.PUNCTUATION, "[")) {
            // skip over opening '[' bracket
            stream.next();

            node = new GetAttributeExpression(node, parseExpression(), stream.getFilename(), stream.current()
                    .getLineNumber());

            // move past the closing ']' bracket
            stream.expect(Type.PUNCTUATION, "]");
        }

        return node;
    }

    public ArgumentsNode parseArguments() throws Exception {
        return parseArguments(false);
    }

    public ArgumentsNode parseArguments(boolean isMacroDefinition) throws Exception {

        List<PositionalArgumentNode> positionalArgs = new ArrayList<>();
        List<NamedArgumentNode> namedArgs = new ArrayList<>();
        this.stream = this.parser.getStream();

        stream.expect(Type.PUNCTUATION, "(");

        while (!stream.current().test(Type.PUNCTUATION, ")")) {

            String argumentName = null;
            Expression<?> argumentValue = null;

            if (!namedArgs.isEmpty() || !positionalArgs.isEmpty()) {
                stream.expect(Type.PUNCTUATION, ",");
            }

            /*
             * Most arguments consist of VALUES with optional NAMES but in the
             * case of a macro definition the user is specifying NAMES with
             * optional VALUES. Therefore the logic changes slightly.
             */
            if (isMacroDefinition) {
                argumentName = parseNewVariableName();
                if (stream.current().test(Type.PUNCTUATION, "=")) {
                    stream.expect(Type.PUNCTUATION, "=");
                    argumentValue = parseExpression();
                }
            } else {
                if (stream.peek().test(Type.PUNCTUATION, "=")) {
                    argumentName = parseNewVariableName();
                    stream.expect(Type.PUNCTUATION, "=");
                }
                argumentValue = parseExpression();
            }

            if (argumentName == null) {
                if (!namedArgs.isEmpty()) {
                    String msg = String.format("Positional arguments must be declared before any named arguments at line %s in file %s.", stream.current()
                            .getLineNumber(), stream.getFilename());
                    throw new Exception(msg);
                }
                positionalArgs.add(new PositionalArgumentNode(argumentValue));
            } else {
                namedArgs.add(new NamedArgumentNode(argumentName, argumentValue));
            }

        }

        stream.expect(Type.PUNCTUATION, ")");

        return new ArgumentsNode(positionalArgs, namedArgs, stream.current().getLineNumber());
    }

    /**
     * Parses a new variable that will need to be initialized in the Java code.
     *
     * This is used for the set tag, the for loop, and in named arguments.
     *
     * @return A variable name
     * @throws Exception Thrown if a parsing error occurs.
     */
    public String parseNewVariableName() throws Exception {

        // set the stream because this function may be called externally (for
        // and set token parsers)
        this.stream = this.parser.getStream();
        Token token = stream.current();
        token.test(Type.NAME);

        if (RESERVED_KEYWORDS.contains(token.getValue())) {
            String msg = String.format("Can not assign a value to %s at line %s in file %s.", token.getValue(), token.getLineNumber(), stream.getFilename());
            throw new Exception(msg);
        }

        stream.next();
        return token.getValue();
    }

    private Expression<?> parseArrayDefinitionExpression() throws Exception {
        TokenStream stream = parser.getStream();

        // expect the opening bracket and check for an empty array
        stream.expect(Type.PUNCTUATION, "[");
        if (stream.current().test(Type.PUNCTUATION, "]")) {
            stream.next();
            return new ArrayExpression(stream.current().getLineNumber());
        }

        // there's at least one expression in the array
        List<Expression<?>> elements = new ArrayList<>();
        while (true) {
            Expression<?> expr = parseExpression();
            elements.add(expr);
            if (stream.current().test(Type.PUNCTUATION, "]")) {
                // this seems to be the end of the array
                break;
            }
            // expect the comma separator, until we either find a closing
            // bracket or fail the expect
            stream.expect(Type.PUNCTUATION, ",");
        }

        // expect the closing bracket
        stream.expect(Type.PUNCTUATION, "]");

        return new ArrayExpression(elements, stream.current().getLineNumber());
    }

    private Expression<?> parseMapDefinitionExpression() throws Exception {
        TokenStream stream = parser.getStream();

        // expect the opening brace and check for an empty map
        stream.expect(Type.PUNCTUATION, "{");
        if (stream.current().test(Type.PUNCTUATION, "}")) {
            stream.next();
            return new MapExpression(stream.current().getLineNumber());
        }

        // there's at least one expression in the map
        Map<Expression<?>, Expression<?>> elements = new HashMap<>();
        while (true) {
            // key : value
            Expression<?> keyExpr = parseExpression();
            stream.expect(Type.PUNCTUATION, ":");
            Expression<?> valueExpr = parseExpression();
            elements.put(keyExpr, valueExpr);
            if (stream.current().test(Type.PUNCTUATION, "}")) {
                // this seems to be the end of the map
                break;
            }
            // expect the comma separator, until we either find a closing brace
            // or fail the expect
            stream.expect(Type.PUNCTUATION, ",");
        }

        // expect the closing brace
        stream.expect(Type.PUNCTUATION, "}");

        return new MapExpression(elements, stream.current().getLineNumber());
    }

}
