package expressionLanguage.model.template;

import java.util.ArrayList;

/**
 * A data structure that represents the entire inheritance hierarchy
 * of the current template and tracks which level in the hierarchy
 * we are currently evaluating.
 */
public class Hierarchy {

    /**
     * A list of all the templates in this hierarchy. A template at index i is the child
     * to the template at index i+1.
     */
    private final ArrayList<Template> hierarchy = new ArrayList<>();

    /**
     * Index of the template currently being evaluated.
     */
    private int current = 0;

    /**
     * Constructs an inheritance chain with one known template.
     *
     * @param current The current node
     */
    public Hierarchy(Template current) {
        hierarchy.add(current);
    }

    /**
     * Adds a known ancestor onto the inheritance chain, does not
     * increment which template is the "current" template being evaluated.
     *
     * @param ancestor The ancestor template
     */
    public void pushAncestor(Template ancestor) {
        hierarchy.add(ancestor);
    }

    /**
     * Signifies that the parent template in the hierarchy is now being evaluated so it should
     * be considered the "current" template.
     */
    public void ascend() {
        current++;
    }

    /**
     * Signifies that the child template in the hierarchy is now being evaluated so i t
     * should be considered the "current" template.
     */
    public void descend() {
        current--;
    }

    /**
     * Returns the child of the template currently being evaluated or null if there is no child.
     *
     * @return The child template if exists or null
     */
    public Template getChild() {
        if (current == 0) {
            return null;
        }
        return hierarchy.get(current - 1);
    }

    /**
     * Returns the parent of the template currently being evaluated or null if there is no parent.
     *
     * @return The parent template if exists or null
     */
    public Template getParent() {
        if (current == hierarchy.size() - 1) {
            return null;
        }
        return hierarchy.get(current + 1);
    }
    
    public Template get() {
        return hierarchy.get(current);
    }
}
