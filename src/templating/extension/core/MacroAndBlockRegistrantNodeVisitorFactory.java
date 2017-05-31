package templating.extension.core;

import templating.template.Template;
import templating.extension.NodeVisitor;
import templating.extension.NodeVisitorFactory;

/**
 * Implementation of {@link NodeVisitorFactory} to handle
 * {@link MacroAndBlockRegistrantNodeVisitor}.
 *
 * @author hunziker
 *
 */
public class MacroAndBlockRegistrantNodeVisitorFactory implements NodeVisitorFactory {

    @Override
    public NodeVisitor createVisitor(Template template) {
        return new MacroAndBlockRegistrantNodeVisitor((Template)template);
    }

}
