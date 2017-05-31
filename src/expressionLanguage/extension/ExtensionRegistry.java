package expressionLanguage.extension;

import expressionLanguage.filter.Filter;
import expressionLanguage.function.Function;
import expressionLanguage.operator.BinaryOperator;
import expressionLanguage.operator.UnaryOperator;
import expressionLanguage.test.Test;
import expressionLanguage.token.parser.TokenParser;
import java.util.*;
import templating.extension.NodeVisitorFactory;

/**
 * Storage for the extensions and the components retrieved from the various
 * extensions.
 */
public class ExtensionRegistry {

    /**
     * Extensions
     */
    private final HashMap<Class<? extends Extension>, Extension> extensions = new HashMap<>();

    /**
     * Unary operators used during the lexing phase.
     */
    private final Map<String, UnaryOperator> unaryOperators = new HashMap<>();

    /**
     * Binary operators used during the lexing phase.
     */
    private final Map<String, BinaryOperator> binaryOperators = new HashMap<>();

    /**
     * Token parsers used during the parsing phase.
     */
    private final Map<String, TokenParser> tokenParsers = new HashMap<>();

    /**
     * Node visitors available during the parsing phase.
     */
    private final List<NodeVisitorFactory> nodeVisitors = new ArrayList<>();

    /**
     * Filters used during the evaluation phase.
     */
    private final Map<String, Filter> filters = new HashMap<>();

    /**
     * Tests used during the evaluation phase.
     */
    private final Map<String, Test> tests = new HashMap<>();

    /**
     * Functions used during the evaluation phase.
     */
    private final Map<String, Function> functions = new HashMap<>();

    /**
     * Global variables available during the evaluation phase.
     */
    private final Map<String, Object> globalVariables = new HashMap<>();

    public ExtensionRegistry(Collection<? extends Extension> extensions) {
        addExtensions(extensions);
    }
    
    private void addExtensions(Collection<? extends Extension> extensions) {
        extensions.stream().forEach(this::addExtension);
    }
    
    private void addExtension(Extension extension) {
        
        List<TokenParser> tParsers = extension.getTokenParsers();
        if (tParsers != null) {
            tParsers.stream().forEach((tokenParser) -> {
                this.tokenParsers.put(tokenParser.getTag(), tokenParser);
            });
        }
        // binary operators
        List<BinaryOperator> bOperators = extension.getBinaryOperators();
        if (bOperators != null) {
            bOperators.stream().filter((operator) -> (!this.binaryOperators.containsKey(operator.getSymbol()))).forEach((operator) -> {
                // disallow overriding core operators
                this.binaryOperators.put(operator.getSymbol(), operator);
            });
        }
        // unary operators
        List<UnaryOperator> uOperators = extension.getUnaryOperators();
        if (uOperators != null) {
            uOperators.stream().filter((operator) -> (!this.unaryOperators.containsKey(operator.getSymbol()))).forEach((operator) -> {
                // disallow override core operators
                this.unaryOperators.put(operator.getSymbol(), operator);
            });
        }
        // filters
        Map<String, Filter> efilters = extension.getFilters();
        if (efilters != null) {
            this.filters.putAll(efilters);
        }
        // tests
        Map<String, Test> etests = extension.getTests();
        if (etests != null) {
            this.tests.putAll(etests);
        }
        // tests
        Map<String, Function> efunctions = extension.getFunctions();
        if (efunctions != null) {
            this.functions.putAll(efunctions);
        }
        // global variables
        Map<String, Object> eglobalVariables = extension.getGlobalVariables();
        if (eglobalVariables != null) {
            this.globalVariables.putAll(eglobalVariables);
        }
        
        List<NodeVisitorFactory> nVisitors = extension.getNodeVisitors();
        
        if (nVisitors != null) {
            this.nodeVisitors.addAll(nVisitors);
        }
        
        this.extensions.put(extension.getClass(), extension);
    }

    public Filter getFilter(String name) {
        return this.filters.get(name);
    }

    public Test getTest(String name) {
        return this.tests.get(name);
    }

    public Function getFunction(String name) {
        return this.functions.get(name);
    }

    public Map<String, BinaryOperator> getBinaryOperators() {
        return this.binaryOperators;
    }

    public Map<String, UnaryOperator> getUnaryOperators() {
        return this.unaryOperators;
    }

    public List<NodeVisitorFactory> getNodeVisitors() {
        return this.nodeVisitors;
    }

    public Map<String, Object> getGlobalVariables() {
        return this.globalVariables;
    }

    public Map<String, TokenParser> getTokenParsers() {
        return this.tokenParsers;
    }
}
