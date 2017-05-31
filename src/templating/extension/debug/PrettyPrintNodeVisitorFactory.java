package templating.extension.debug;

import templating.template.Template;
import templating.extension.NodeVisitor;
import templating.extension.NodeVisitorFactory;

/**
 * Implementation of {@link NodeVisitorFactory} to create
 * {@link PrettyPrintNodeVisitor}.
 */
public class PrettyPrintNodeVisitorFactory implements NodeVisitorFactory {

    @Override
    public NodeVisitor createVisitor(Template template) {
       return new PrettyPrintNodeVisitor(template);
    }

}
