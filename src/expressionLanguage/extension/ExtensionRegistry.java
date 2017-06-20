package expressionLanguage.extension;

import expressionLanguage.filter.Filter;
import expressionLanguage.function.Function;
import expressionLanguage.model.visitor.NodeVisitor;
import expressionLanguage.operator.Operator;
import expressionLanguage.test.Test;
import expressionLanguage.token.parser.TokenParser;
import java.util.*;

/**
 * Storage for the extensions and the components retrieved from the various
 * extensions.
 */
public class ExtensionRegistry {

    /**
     * Extensions
     */
    private final Collection<Extension> extensions = new ArrayList();
    
    /**
     * Operators used during the lexing phase.
     */
    private Map<String, Operator> operators;

    /**
     * Token parsers used during the parsing phase.
     */
    private Map<String, TokenParser> tokenParsers = new HashMap<>();

    /**
     * Visitors available during the parsing phase.
     */
    private final List<NodeVisitor> visitors = new ArrayList<>();

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
    
    public ExtensionRegistry() {
    }
    
    public ExtensionRegistry(Collection<Extension> extensions) {
        addExtensions(extensions);
    }
    
    private void addExtensions(Collection<Extension> extensions) {
        extensions.forEach(this::addExtension);
    }
    
    private void addExtension(Extension extension) {
        
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
        
        List<NodeVisitor> visitors = extension.getVisitors();
        
        if (visitors != null) {
            this.visitors.addAll(visitors);
        }
        
        this.extensions.add(extension);
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

    public Map<String, Operator> getOperators() {
        if (this.operators == null) {
            this.operators = new HashMap<>();
        }

        this.extensions.stream()
                .map((extension) -> extension.getOperators())
                .forEach(this.operators::putAll);

        return operators;
    }

    public List<NodeVisitor> getVisitors() {
        return this.visitors;
    }

    public Map<String, Object> getGlobalVariables() {
        return this.globalVariables;
    }

    public Map<String, TokenParser> getTokenParsers() {
        if (this.tokenParsers == null) {
            this.tokenParsers = new HashMap<>();
        }

        this.extensions.stream()
                .map((extension) -> extension.getTokenParsers())
                .forEach(this.tokenParsers::putAll);

        return tokenParsers;
    }
    
    public static ExtensionRegistryBuilder builder() {
        return new ExtensionRegistryBuilder();
    }
    
    public static class ExtensionRegistryBuilder {
        
        private Collection<Extension> extensions = new ArrayList<>();
        
        public ExtensionRegistryBuilder with(Extension extension) {
            this.extensions.add(extension);
            return this;
        }
        
        public ExtensionRegistryBuilder and(Extension extension) {
            return with(extension);
        }
        
        public ExtensionRegistry build() {
            ExtensionRegistry registry = new ExtensionRegistry();
            
            registry.addExtensions(extensions);
            
            return registry;
        }
    }
}
