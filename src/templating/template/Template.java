package templating.template;

import templating.Hierarchy;
import templating.EvaluationContext;
import templating.Engine;
import templating.FutureWriter;
import templating.scope.ScopeChain;
import templating.extension.escaper.SafeString;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import templating.node.ArgumentsNode;
import templating.node.RootNode;

/**
 * The actual implementation of a TemplateInterface
 */
public class Template implements TemplateInterface {

    /**
     * A template has to store a reference to the main engine so that it can
     * compile other templates when using the "import" or "include" tags.
     * <p>
     * It will also retrieve some stateful information such as the default locale
     * when necessary. Luckily, the engine is immutable so this should be thread safe.
     */
    private final Engine engine;

    /**
     * Blocks defined inside this template.
     */
    private final Map<String, Block> blocks = new HashMap<>();

    /**
     * Macros defined inside this template.
     */
    private final Map<String, Macro> macros = new HashMap<>();

    /**
     * The root node of the AST to be rendered.
     */
    private final RootNode rootNode;

    /**
     * Name of template. Used to help with debugging.
     */
    private final String name;

    /**
     * Constructor
     *
     * @param engine The engine used to construct this template
     * @param root   The root not to evaluate
     * @param name   The name of the template
     */
    public Template(Engine engine, RootNode root, String name) {
        this.engine = engine;
        this.rootNode = root;
        this.name = name;
    }

    @Override
    public void evaluate(Writer writer) throws Exception {
        EvaluationContext context = initContext(null);
        evaluate(writer, context);
    }

    @Override
    public void evaluate(Writer writer, Locale locale) throws Exception {
        EvaluationContext context = initContext(locale);
        evaluate(writer, context);
    }

    @Override
    public void evaluate(Writer writer, Map<String, Object> map) throws Exception {
        EvaluationContext context = initContext(null);
        context.getScopeChain().pushScope(map);
        evaluate(writer, context);
    }

    @Override
    public void evaluate(Writer writer, Map<String, Object> map, Locale locale) throws Exception {
        EvaluationContext context = initContext(locale);
        context.getScopeChain().pushScope(map);
        evaluate(writer, context);
    }

    /**
     * This is the authoritative evaluate method. It will evaluate the template
     * starting at the root node.
     *
     * @param writer  The writer used to write the final output of the template
     * @param context The evaluation context
     * @throws PebbleException Thrown if any sort of template error occurs
     * @throws IOException     Thrown from the writer object
     */
    private void evaluate(Writer writer, EvaluationContext context) throws Exception {
        if (context.getExecutorService() != null) {
            writer = new FutureWriter(writer);
        }
        rootNode.render(this, writer, context);

        /*
         * If the current template has a parent then we know the current template
         * was only used to evaluate a very small subset of tags such as "set" and "import".
         * We now evaluate the parent template as to evaluate all of the actual content.
         * When evaluating the parent template, it will check the child template for overridden blocks.
         */
        if (context.getHierarchy().getParent() != null) {
            Template parent = context.getHierarchy().getParent();
            context.getHierarchy().ascend();
            parent.evaluate(writer, context);
        }
        writer.flush();
    }

    /**
     * Initializes the evaluation context with settings from the engine.
     *
     * @param locale The desired locale
     * @return The evaluation context
     */
    private EvaluationContext initContext(Locale locale) {
        locale = locale == null ? engine.getDefaultLocale() : locale;

        // globals
        Map<String, Object> globals = new HashMap<>();
        globals.put("locale", locale);
        globals.put("template", this);
        ScopeChain scopeChain = new ScopeChain(globals);

        // global vars provided from extensions
        scopeChain.pushScope(engine.getExtensionRegistry().getGlobalVariables());

        EvaluationContext context = new EvaluationContext(this, engine.isStrictVariables(), locale,
                engine.getExtensionRegistry(), engine.getExecutorService(),
                new ArrayList<>(), scopeChain, null);
        return context;
    }

    /**
     * Imports a template.
     *
     * @param context The evaluation context
     * @param name    The template name
     * @throws Exception Thrown if an error occurs while rendering the imported template
     */
    public void importTemplate(EvaluationContext context, String name) throws Exception {
        context.getImportedTemplates().add((Template) engine.getTemplate(this.resolveRelativePath(name)));
    }

    /**
     * Includes a template with {@code name} into this template.
     *
     * @param writer              the writer to which the output should be written to.
     * @param context             the context within which the template is rendered in.
     * @param name                the name of the template to include.
     * @param additionalVariables the map with additional variables provided with the include
     *                            tag to add within the include tag.
     * @throws Exception          Any error occurring during the compilation of the template
     */
    public void includeTemplate(Writer writer, EvaluationContext context, String name, Map<?, ?> additionalVariables) throws Exception {
        Template template = (Template) engine.getTemplate(this.resolveRelativePath(name));
        EvaluationContext newContext = context.shallowCopyWithoutInheritanceChain(template);
        ScopeChain scopeChain = newContext.getScopeChain();
        scopeChain.pushScope();
        additionalVariables.entrySet().stream().forEach((entry) -> {
            scopeChain.put((String) entry.getKey(), entry.getValue());
        });
        template.evaluate(writer, newContext);
        scopeChain.popScope();
    }

    /**
     * Checks if a macro exists
     *
     * @param macroName The name of the macro
     * @return Whether or not the macro exists
     */
    public boolean hasMacro(String macroName) {
        return macros.containsKey(macroName);
    }

    /**
     * This method resolves the given relative path based on this template file
     * path.
     *
     * @param relativePath the path which should be resolved.
     * @return the resolved path.
     */
    public String resolveRelativePath(String relativePath) {
        String resolved = this.engine.getLoader().resolveRelativePath(relativePath, this.name);
        if (resolved == null) {
            return relativePath;
        } else {
            return resolved;
        }
    }

    /**
     * Registers a block.
     *
     * @param block The block
     */
    public void registerBlock(Block block) {
        blocks.put(block.getName(), block);
    }

    /**
     * Registers a macro
     *
     * @param macro The macro
     * @throws Exception Throws exception if macro already exists with the same name
     */
    public void registerMacro(Macro macro) throws Exception {
        if (macros.containsKey(macro.getName())) {
            String msg = String.format("More than one macro can not share the same name: %s", macro.getName());
            throw new Exception(msg);
        }
        this.macros.put(macro.getName(), macro);
    }

    /**
     * A typical block declaration will use this method which evaluates the
     * block using the regular user-provided writer.
     *
     * @param blockName       The name of the block
     * @param context         The evaluation context
     * @param ignoreOverriden Whether or not to ignore overriden blocks
     * @param writer          The writer
     * @throws Exception      Thrown if an error occurs
     */
    public void block(Writer writer, EvaluationContext context, String blockName, boolean ignoreOverriden) throws Exception {

        Hierarchy hierarchy = context.getHierarchy();
        Template childTemplate = hierarchy.getChild();

        // check child
        if (!ignoreOverriden && childTemplate != null) {
            hierarchy.descend();
            childTemplate.block(writer, context, blockName, false);
            hierarchy.ascend();

            // check this template
        } else if (blocks.containsKey(blockName)) {
            Block block = blocks.get(blockName);
            block.evaluate(this, writer, context);

            // delegate to parent
        } else {
            if (hierarchy.getParent() != null) {
                Template parent = hierarchy.getParent();
                hierarchy.ascend();
                parent.block(writer, context, blockName, true);
                hierarchy.descend();
            }
        }

    }

    /**
     * Invokes a macro
     *
     * @param context         The evaluation context
     * @param macroName       The name of the macro
     * @param args            The arguments
     * @param ignoreOverriden Whether or not to ignore macro definitions in child template
     * @return The results of the macro invocation
     * @throws Exception      An exception that may have occurred
     */
    public SafeString macro(EvaluationContext context, String macroName, ArgumentsNode args, boolean ignoreOverriden) throws Exception {
        SafeString result = null;
        boolean found = false;

        Template childTemplate = context.getHierarchy().getChild();

        // check child template first
        if (!ignoreOverriden && childTemplate != null) {
            found = true;
            context.getHierarchy().descend();
            result = childTemplate.macro(context, macroName, args, false);
            context.getHierarchy().ascend();

            // check current template
        } else if (hasMacro(macroName)) {
            found = true;
            Macro macro = macros.get(macroName);

            Map<String, Object> namedArguments = args.getArgumentMap(this, context, macro);
            result = new SafeString(macro.call(this, context, namedArguments));
        }

        // check imported templates
        if (!found) {
            for (Template template : context.getImportedTemplates()) {
                if (template.hasMacro(macroName)) {
                    found = true;
                    result = template.macro(context, macroName, args, false);
                }
            }
        }

        // delegate to parent template
        if (!found) {
            if (context.getHierarchy().getParent() != null) {
                Template parent = context.getHierarchy().getParent();
                context.getHierarchy().ascend();
                result = parent.macro(context, macroName, args, true);
                context.getHierarchy().descend();
            } else {
                String msg = String.format("Function or Macro [%s] does not exist.", macroName);
                throw new Exception(msg);
            }
        }

        return result;
    }

    public void setParent(EvaluationContext context, String parentName) throws Exception {
        context.getHierarchy().pushAncestor((Template) engine.getTemplate(this.resolveRelativePath(parentName)));
    }

    /**
     * Returns the template name
     *
     * @return The name of the template
     */
    @Override
    public String getName() {
        return name;
    }

}
