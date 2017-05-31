package expressionLanguage.extension;

import expressionLanguage.filter.Filter;
import expressionLanguage.function.Function;
import expressionLanguage.operator.BinaryOperator;
import expressionLanguage.operator.UnaryOperator;
import expressionLanguage.test.Test;
import expressionLanguage.token.parser.TokenParser;
import java.util.List;
import java.util.Map;
import templating.extension.NodeVisitorFactory;

public interface Extension {

    /**
     * Use this method to provide custom filters.
     *
     * @return A list of filters. It is okay to return null.
     */
    default Map<String, Filter> getFilters() {
        return null;
    }

    /**
     * Use this method to provide custom tests.
     *
     * @return A list of tests. It is okay to return null.
     */
    default Map<String, Test> getTests(){
        return null;
    }

    /**
     * Use this method to provide custom functions.
     *
     * @return A list of functions. It is okay to return null.
     */
    default Map<String, Function> getFunctions(){
        return null;
    }

    /**
     * Use this method to provide custom tags.
     *
     * A TokenParser is used to parse a stream of tokens into Nodes which are
     * then responsible for compiling themselves into Java.
     *
     * @return A list of TokenParsers. It is okay to return null.
     */
    default List<TokenParser> getTokenParsers(){
        return null;
    }

    /**
     * Use this method to provide custom binary operators.
     *
     * @return A list of Operators. It is okay to return null;
     */
    default List<BinaryOperator> getBinaryOperators(){
        return null;
    }

    /**
     * Use this method to provide custom unary operators.
     *
     * @return A list of Operators. It is okay to return null;
     */
    default List<UnaryOperator> getUnaryOperators(){
        return null;
    }

    /**
     * Use this method to provide variables available to all templates
     *
     * @return Map of global variables available to all templates
     */
    default Map<String, Object> getGlobalVariables(){
        return null;
    }

    /**
     * Node visitors will travel the AST tree during the compilation phase.
     *
     * @return a list of node visitors
     */
    default List<NodeVisitorFactory> getNodeVisitors(){
        return null;
    }
}
