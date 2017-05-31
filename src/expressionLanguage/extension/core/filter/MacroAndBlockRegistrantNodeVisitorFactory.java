package expressionLanguage.extension.core.filter;

import templating.template.Template;
import templating.extension.NodeVisitor;
import templating.extension.NodeVisitorFactory;

/**
 * Implementation of {@link NodeVisitorFactory} to handle
 * {@link MacroAndBlockRegistrantNodeVisitor}.
 *
 */
public class MacroAndBlockRegistrantNodeVisitorFactory implements NodeVisitorFactory {

    @Override
    public NodeVisitor createVisitor(Template template) {
        return new MacroAndBlockRegistrantNodeVisitor((Template)template);
    }

}
