package templating;

import templating.extension.Extension;
import templating.extension.ExtensionRegistry;
import templating.extension.core.CoreExtension;
import templating.extension.escaper.EscaperExtension;
import templating.extension.escaper.EscapingStrategy;
import templating.extension.i18n.I18nExtension;
import templating.lexer.Lexer;
import templating.lexer.Syntax;
import templating.loader.ClasspathLoader;
import templating.loader.DelegatingLoader;
import templating.loader.FileLoader;
import templating.loader.LoaderInterface;
import templating.parser.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

public class EngineBuilder {

    private LoaderInterface loader;

    private final List<Extension> userProvidedExtensions = new ArrayList<>();

    private Syntax syntax = new Syntax();

    private boolean strictVariables = false;

    private Locale defaultLocale;

    private ExecutorService executorService;

    private final EscaperExtension escaperExtension = new EscaperExtension();

    /**
     * Creates the builder.
     */
    public EngineBuilder() {

    }

    /**
     * Sets the loader used to find templates.
     *
     * @param loader A template loader
     * @return This builder object
     */
    public EngineBuilder loader(LoaderInterface loader) {
        this.loader = loader;
        return this;
    }

    /**
     * Adds an extension, can be safely invoked several times to add different
     * extensions.
     *
     * @param extensions One or more extensions to add
     * @return This builder object
     */
    public EngineBuilder extension(Extension... extensions) {
        this.userProvidedExtensions.addAll(Arrays.asList(extensions));
        return this;
    }

    /**
     * Sets the syntax to be used.
     *
     * @param syntax The syntax to be used
     * @return This builder object
     */
    public EngineBuilder syntax(Syntax syntax) {
        this.syntax = syntax;
        return this;
    }

    /**
     * Changes the <code>strictVariables</code> setting of the PebbleEngine. The
     * default value of this setting is "false".
     * <p>
     * The following examples will all print empty strings if strictVariables is
     * false but will throw exceptions if it is true:
     * </p>
     * {{ nonExistingVariable }} {{ nonExistingVariable.attribute }} {{
     * nullVariable.attribute }} {{ existingVariable.nullAttribute.attribute }}
     * {{ existingVariable.nonExistingAttribute }} {{ array[-1] }}
     *
     * @param strictVariables Whether or not strict variables is used
     * @return This builder object
     */
    public EngineBuilder strictVariables(boolean strictVariables) {
        this.strictVariables = strictVariables;
        return this;
    }

    /**
     * Sets the Locale passed to all templates constructed by this PebbleEngine.
     * <p>
     * An individual template can always be given a new locale during
     * evaluation.
     *
     * @param defaultLocale The default locale
     * @return This builder object
     */
    public EngineBuilder defaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
        return this;
    }

    /**
     * Sets the executor service which is required if using one of Pebble's
     * multithreading features such as the "parallel" tag.
     *
     * @param executorService The executor service
     * @return This builder object
     */
    public EngineBuilder executorService(ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    /**
     * Sets whether or not escaping should be performed automatically.
     *
     * @param autoEscaping The auto escaping setting
     * @return This builder object
     */
    public EngineBuilder autoEscaping(boolean autoEscaping) {
        escaperExtension.setAutoEscaping(autoEscaping);
        return this;
    }

    /**
     * Sets the default escaping strategy of the built-in escaper extension.
     *
     * @param strategy The name of the default escaping strategy
     * @return This builder object
     */
    public EngineBuilder defaultEscapingStrategy(String strategy) {
        escaperExtension.setDefaultStrategy(strategy);
        return this;
    }

    /**
     * Adds an escaping strategy to the built-in escaper extension.
     *
     * @param name The name of the escaping strategy
     * @param strategy The strategy implementation
     * @return This builder object
     */
    public EngineBuilder addEscapingStrategy(String name, EscapingStrategy strategy) {
        escaperExtension.addEscapingStrategy(name, strategy);
        return this;
    }

    /**
     * Creates the Engine instance.
     *
     * @return A Engine object that can be used to create Template objects.
     */
    public Engine build() {

        // core extensions
        List<Extension> extensions = new ArrayList<>();
        extensions.add(new CoreExtension());
        extensions.add(escaperExtension);
        extensions.add(new I18nExtension());
        extensions.addAll(this.userProvidedExtensions);

        // default loader
        if (loader == null) {
            List<LoaderInterface> defaultLoadingStrategies = new ArrayList<>();
            defaultLoadingStrategies.add(new ClasspathLoader());
            defaultLoadingStrategies.add(new FileLoader());
            loader = new DelegatingLoader(defaultLoadingStrategies);
        }

        // default locale
        if (defaultLocale == null) {
            defaultLocale = Locale.getDefault();
        }
        
        syntax.compile();

        ExtensionRegistry registry = new ExtensionRegistry(extensions);
        Lexer lexer = new Lexer(syntax, registry.getUnaryOperators().values(), registry.getBinaryOperators().values());
        Parser parser = new Parser(registry.getUnaryOperators(), registry.getBinaryOperators(), registry.getTokenParsers());

        return new Engine(loader, lexer, parser, registry, strictVariables, defaultLocale, executorService);
    }
}
