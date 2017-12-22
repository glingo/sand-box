package component.expressionLanguage;

import component.expressionLanguage.extension.ExtensionRegistry;
import component.expressionLanguage.lexer.Lexer;
import component.expressionLanguage.model.position.Source;
import component.expressionLanguage.model.template.Template;
import component.expressionLanguage.model.tree.RootNode;
import component.expressionLanguage.model.visitor.BlockRegistrantVisitor;
import component.expressionLanguage.model.visitor.MacroRegistrantVisitor;
import component.expressionLanguage.token.parser.Parser;
import component.expressionLanguage.scope.ScopeChain;
import component.expressionLanguage.token.TokenStream;
import component.resource.ResourceService;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * The main class used for compiling templates. The PebbleEngine is responsible
 * for delegating responsibility to the lexer, parser, compiler, and template
 * cache.
 */
public class Engine {

    private final ResourceService resourceService;

    private final Lexer lexer;

    private final Parser<TokenStream, RootNode> parser;

    private final boolean strictVariables;

    private final ExtensionRegistry extensionRegistry;

    private final Locale defaultLocale;

    private final ExecutorService executorService;

    public Engine(ResourceService resourceService,
            Lexer lexer,
            Parser parser,
            ExtensionRegistry extensionRegistry,
            boolean strictVariables,
            Locale defaultLocale,
            ExecutorService executorService) {

        this.resourceService = resourceService;
        this.lexer = lexer;
        this.parser = parser;
        this.extensionRegistry = extensionRegistry;
        this.strictVariables = strictVariables;
        this.defaultLocale = defaultLocale;
        this.executorService = executorService;
    }

    /**
     * Loads, parses, and compiles a template into an instance of PebbleTemplate
     * and returns this instance.
     *
     * @param name The name of the template
     * @return Template The compiled version of the template
     * @throws Exception Thrown if an error occurs while parsing the template.
     */
    public Template load(final String name) throws Exception {

        /*
         * template name will be null if user uses the extends tag with an
         * expression that evaluates to null
         */
        if (name == null) {
            return null;
        }

        InputStream stream = this.resourceService.load(name);
        Reader reader = new InputStreamReader(stream);
        Source source = Source.builder(name).read(reader).build();
        TokenStream tokenStream = this.lexer.tokenize(source);
        RootNode root = this.parser.parse(tokenStream);

//        final Engine self = this;
        Template instance = new Template(this, root, name);

        evaluate(instance);

        return instance;
    }

    public void evaluate(Template root) throws Exception {
        EvaluationContext context = initContext(root, null);
        evaluate(root, context);
    }

    public void evaluate(Template root, Locale locale) throws Exception {
        EvaluationContext context = initContext(root, locale);
        evaluate(root, context);
    }

    public void evaluate(Template root, Map<String, Object> map) throws Exception {
        EvaluationContext context = initContext(root, null);
        context.getScopeChain().pushScope(map);
        evaluate(root, context);
    }

    public void evaluate(Template root, Map<String, Object> map, Locale locale) throws Exception {
        EvaluationContext context = initContext(root, locale);
        context.getScopeChain().pushScope(map);
        evaluate(root, context);
    }

    /**
     * This is the authoritative evaluate method. It will evaluate the template
     * starting at the root node.
     *
     * @param writer The writer used to write the final output of the template
     * @param context The evaluation context
     * @throws Exception Thrown from the writer object
     */
    private void evaluate(Template root, EvaluationContext context) throws Exception {

//        root.accept((node) -> {
//            System.out.println("Node : ");
//            System.out.println(Objects.toString(node));
//        });
//        System.out.println(root);
        // Eval logic HERE 
//        template.
//        rootNode.render(this, context);

        Stream.concat(
            Stream.of(new BlockRegistrantVisitor(root), new MacroRegistrantVisitor(root)), 
            this.extensionRegistry.getVisitors().stream()
        ).forEach(root.getRootNode()::accept);

        /*
         * If the current template has a parent then we know the current template
         * was only used to evaluate a very small subset of tags such as "set" and "import".
         * We now evaluate the parent template as to evaluate all of the actual content.
         * When evaluating the parent template, it will check the child template for overridden blocks.
         */
        if (context.getHierarchy().getParent() != null) {
            Template parent = context.getHierarchy().getParent();
            context.getHierarchy().ascend();
            evaluate(parent, context);
        }
    }

    /**
     * Initializes the evaluation context with settings from the engine.
     *
     * @param locale The desired locale
     * @return The evaluation context
     */
    private EvaluationContext initContext(Template root, Locale locale) {
        locale = locale == null ? getDefaultLocale() : locale;

        // globals
        Map<String, Object> globals = new HashMap<>();
        globals.put("locale", locale);
        globals.put("template", root);
        ScopeChain scopeChain = new ScopeChain(globals);

        // global vars provided from extensions
        scopeChain.pushScope(getExtensionRegistry().getGlobalVariables());

        EvaluationContext context = new EvaluationContext(root,
                isStrictVariables(), locale,
                getExtensionRegistry(), getExecutorService(),
                new ArrayList<>(), scopeChain, null);
        return context;
    }

    /**
     * Returns the resourceService
     *
     * @return The resourceService
     */
    public ResourceService getResourceService() {
        return resourceService;
    }

    /**
     * Returns the strict variables setting
     *
     * @return The strict variables setting
     */
    public boolean isStrictVariables() {
        return strictVariables;
    }

    /**
     * Returns the default locale
     *
     * @return The default locale
     */
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * Returns the executor service
     *
     * @return The executor service
     */
    public ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * Returns the extension registry.
     *
     * @return The extension registry
     */
    public ExtensionRegistry getExtensionRegistry() {
        return extensionRegistry;
    }

}
