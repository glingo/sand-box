package component.expressionLanguage.model.template;

import component.expressionLanguage.Engine;
import component.expressionLanguage.EvaluationContext;
import component.expressionLanguage.function.Function;
import component.expressionLanguage.model.tree.ArgumentsNode;
import component.expressionLanguage.model.tree.RootNode;
import java.util.HashMap;
import java.util.Map;

/**
 * The actual implementation of a Template
 */
public class Template {

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
    private final Map<String, Function> macros = new HashMap<>();

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
     */
    public void registerMacro(Function macro) {
        if (macros.containsKey(macro.getName())) {
            String msg = String.format("More than one macro can not share the same name: %s", macro.getName());
            throw new IllegalStateException(msg);
        }
        this.macros.put(macro.getName(), macro);
    }

    public void setParent(EvaluationContext context, String parentName) throws Exception {
        context.getHierarchy().pushAncestor(engine.load(parentName));
    }
    
    /**
     * A typical block declaration will use this method which evaluates the
     * block using the regular user-provided writer.
     *
     * @param blockName       The name of the block
     * @param context         The evaluation context
     * @param ignoreOverriden Whether or not to ignore overriden blocks
     */
    public void block(EvaluationContext context, String blockName, boolean ignoreOverriden) {

        Hierarchy hierarchy = context.getHierarchy();
        Template childTemplate = hierarchy.getChild();

        // check child
        if (!ignoreOverriden && childTemplate != null) {
            hierarchy.descend();
            childTemplate.block(context, blockName, false);
            hierarchy.ascend();

            // check this template
        } else if (blocks.containsKey(blockName)) {
            Block block = blocks.get(blockName);
            block.evaluate(this, context);

            // delegate to parent
        } else {
            if (hierarchy.getParent() != null) {
                Template parent = hierarchy.getParent();
                hierarchy.ascend();
                parent.block(context, blockName, true);
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
     */
    public String macro(EvaluationContext context, String macroName, ArgumentsNode args, boolean ignoreOverriden) {
        String result = null;
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
            Function macro = macros.get(macroName);

            Map<String, Object> namedArguments = args.getArgumentMap(context, macro);
            result = (String) macro.evaluate(context, namedArguments);
        }

        // check imported templates
        if (!found) {
            for (Template template : context.getImported()) {
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
                throw new IllegalStateException(msg);
            }
        }

        return result;
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
     * Returns the template name
     *
     * @return The name of the template
     */
    public String getName() {
        return name;
    }

    public RootNode getRootNode() {
        return rootNode;
    }
    
    @Override
    public String toString() {
        return "Template{" + "engine=" + engine + ", blocks=" + blocks + ", macros=" + macros + ", rootNode=" + rootNode + ", name=" + name + '}';
    }
}
