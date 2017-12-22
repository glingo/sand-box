package component.expressionLanguage;

import component.expressionLanguage.model.template.Hierarchy;
import component.expressionLanguage.extension.ExtensionRegistry;
import component.expressionLanguage.model.template.Template;
import component.expressionLanguage.scope.ScopeChain;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

/**
 * An evaluation context will store all stateful data that is necessary for the
 * evaluation of a template. Passing the entire state around will assist with
 * thread safety.
 *
 */
public class EvaluationContext {

    private final boolean strictVariables;

    /**
     * A template will look to it's parent and children for overridden macros
     * and other features; this inheritance chain will help the template keep
     * track of where in the inheritance chain it currently is.
     */
    private final Hierarchy hierarchy;

    /**
     * A scope is a set of visible variables. A trivial template will only have
     * one scope. New scopes are added with for loops and macros for example.
     * <p>
     * Most scopes will have a link to their parent scope which allow an
     * evaluation to look up the scope chain for variables. A macro is an
     * exception to this as it only has access to it's local variables.
     */
    private final ScopeChain scopeChain;

    /**
     * The locale of this template.
     */
    private final Locale locale;

    /**
     * All the available filters/tests/functions for this template.
     */
    private final ExtensionRegistry extensionRegistry;

    /**
     * The user-provided ExecutorService (can be null).
     */
    private final ExecutorService executorService;

    /**
     * The imported templates are used to look up macros.
     */
    private final List<Template> imported;
    
    /**
     * Constructor used to provide all final variables.
     *
     * @param self The template implementation
     * @param strictVariables Whether strict variables is to be used
     * @param locale The locale of the template
     * @param extensionRegistry The extension registry
     * @param executorService The optional executor service
     * @param imported
     * @param scopeChain The scope chain
     * @param hierarchy The inheritance chain
     */
    public EvaluationContext(Template self, boolean strictVariables, Locale locale,
            ExtensionRegistry extensionRegistry,
            ExecutorService executorService, List<Template> imported, ScopeChain scopeChain,
            Hierarchy hierarchy) {

        if (hierarchy == null) {
            hierarchy = new Hierarchy(self);
        }

        this.strictVariables = strictVariables;
        this.locale = locale;
        this.extensionRegistry = extensionRegistry;
        this.executorService = executorService;
        this.imported = imported;
        this.scopeChain = scopeChain;
        this.hierarchy = hierarchy;
    }

    /**
     * Makes an exact copy of the evaluation context EXCEPT for the inheritance
     * chain. This is necessary for the "include" tag.
     *
     * @param self The template implementation
     * @return A copy of the evaluation context
     */
    public EvaluationContext shallowCopyWithoutInheritanceChain(Template self) {
        EvaluationContext result = new EvaluationContext(self, strictVariables, locale, extensionRegistry, executorService, imported, scopeChain, null);
        return result;
    }

    /**
     * Makes a "snapshot" of the evaluation context. The scopeChain object will
     * be a deep copy and the imported templates will be a new list. This is
     * used for the "parallel" tag.
     *
     * @param self The template implementation
     * @return A copy of the evaluation context
     */
    public EvaluationContext threadSafeCopy(Template self) {
        EvaluationContext result = new EvaluationContext(self, strictVariables, locale, extensionRegistry, executorService, new ArrayList<>(imported), scopeChain.deepCopy(), hierarchy);
        return result;
    }

    /**
     * Returns whether or not this template is being evaluated in "strict
     * templates" mode
     *
     * @return Whether or not this template is being evaluated in "strict
     * tempaltes" mode.
     */
    public boolean isStrictVariables() {
        return strictVariables;
    }

    /**
     * Returns the locale
     *
     * @return The current locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Returns the extension registry used to access all of the
     * tests/filters/functions
     *
     * @return The extension registry
     */
    public ExtensionRegistry getExtensionRegistry() {
        return extensionRegistry;
    }

    /**
     * Returns the executor service if exists or null
     *
     * @return The executor service if exists, or null
     */
    public ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * Returns a list of imported Templates.
     *
     * @return A list of imported Templates.
     */
    public List<Template> getImported() {
        return this.imported;
    }
    
    /**
     * Returns the scope chain data structure that allows variables to be
     * added/removed from the current scope and retrieved from the nearest
     * visible scopes.
     *
     * @return The scope chain.
     */
    public ScopeChain getScopeChain() {
        return scopeChain;
    }

    /**
     * Returns the data structure representing the entire hierarchy of the
     * template currently being evaluated.
     *
     * @return The inheritance chain
     */
    public Hierarchy getHierarchy() {
        return hierarchy;
    }

}
