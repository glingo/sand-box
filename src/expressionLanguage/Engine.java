package expressionLanguage;


import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * The main class used for compiling templates. The PebbleEngine is responsible
 * for delegating responsibility to the lexer, parser, compiler, and template
 * cache.
 */
public class Engine {

    private final LoaderInterface loader;
    
    private final Lexer lexer;
    
    private final Parser parser;

    private final boolean strictVariables;

    private final Locale defaultLocale;

    private final ExecutorService executorService;

    private final ExtensionRegistry extensionRegistry;

    public Engine(LoaderInterface loader, 
        Lexer lexer,
        Parser parser,
        ExtensionRegistry extensionRegistry,
        boolean strictVariables, 
        Locale defaultLocale, 
        ExecutorService executorService) {

        this.loader = loader;
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
     * @param templateName The name of the template
     * @return Template The compiled version of the template
     * @throws Exception Thrown if an error occurs while parsing the template.
     */
    public Template getTemplate(final String templateName) throws Exception {

        /*
         * template name will be null if user uses the extends tag with an
         * expression that evaluates to null
         */
        if (templateName == null) {
            return null;
        }

        if (this.loader == null) {
            throw new Exception("Loader has not yet been specified.");
        }

        final Engine self = this;
        final Object cacheKey = this.loader.createCacheKey(templateName);

        Reader templateReader = self.retrieveReaderFromLoader(self.loader, cacheKey);
        TokenStream tokenStream = this.lexer.tokenize(templateReader, templateName);

        RootNode root = this.parser.parse(tokenStream);

        Template instance = new Template(self, root, templateName);

        extensionRegistry.getNodeVisitors().stream().forEach((visitorFactory) -> {
            visitorFactory.createVisitor(instance).visit(root);
        });

        return instance;
    }
    
    public void evaluate(Template template) throws Exception {
        EvaluationContext context = initContext(template, null);
        evaluate(template, context);
    }

    public void evaluate(Template template, Locale locale) throws Exception {
        EvaluationContext context = initContext(template, locale);
        evaluate(template, context);
    }

    public void evaluate(Template template, Map<String, Object> map) throws Exception {
        EvaluationContext context = initContext(template, null);
        context.getScopeChain().pushScope(map);
        evaluate(template, context);
    }

    public void evaluate(Template template, Map<String, Object> map, Locale locale) throws Exception {
        EvaluationContext context = initContext(template, locale);
        context.getScopeChain().pushScope(map);
        evaluate(template, context);
    }
    
    /**
     * This is the authoritative evaluate method. It will evaluate the template
     * starting at the root node.
     *
     * @param writer  The writer used to write the final output of the template
     * @param context The evaluation context
     * @throws Exception     Thrown from the writer object
     */
    private void evaluate(Template template, EvaluationContext context) throws Exception {
        
        // Eval logic HERE 
//        template.
        
//        rootNode.render(this, context);

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
    private EvaluationContext initContext(Template template, Locale locale) {
        locale = locale == null ? getDefaultLocale() : locale;

        // globals
        Map<String, Object> globals = new HashMap<>();
        globals.put("locale", locale);
        globals.put("template", template);
        ScopeChain scopeChain = new ScopeChain(globals);

        // global vars provided from extensions
        scopeChain.pushScope(getExtensionRegistry().getGlobalVariables());

        EvaluationContext context = new EvaluationContext(template,
                isStrictVariables(), locale,
                getExtensionRegistry(), getExecutorService(),
                new ArrayList<>(), scopeChain, null);
        return context;
    }


    /**
     * This method calls the loader and fetches the reader. We use this method
     * to handle the generic cast.
     *
     * @param loader the loader to use fetch the reader.
     * @param cacheKey the cache key to use.
     * @return the reader object.
     * @throws Exception thrown when the template could not be loaded.
     */
    private <T> Reader retrieveReaderFromLoader(LoaderInterface<T> loader, Object cacheKey) throws Exception {
        // We make sure within getTemplate() that we use only the same key for
        // the same loader and hence we can be sure that the cast is safe.
        @SuppressWarnings("unchecked")
        T casted = (T) cacheKey;
        return loader.getReader(casted);
    }

    /**
     * Returns the loader
     *
     * @return The loader
     */
    public LoaderInterface<?> getLoader() {
        return loader;
    }

    /**
     * Returns the template cache
     *
     * @return The template cache
     */
//    public Cache<Object, Template> getTemplateCache() {
//        return templateCache;
//    }

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
     * Returns the syntax which is used by this PebbleEngine.
     *
     * @return the syntax used by the PebbleEngine.
     */
//    public Syntax getSyntax() {
//        return this.syntax;
//    }

    /**
     * Returns the extension registry.
     *
     * @return The extension registry
     */
    public ExtensionRegistry getExtensionRegistry() {
        return extensionRegistry;
    }

    /**
     * Returns the tag cache
     *
     * @return The tag cache
     */
//    public Cache<BaseTagCacheKey, Object> getTagCache() {
//        return this.tagCache;
//    }

}
