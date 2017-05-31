package templating.extension.escaper;

import templating.template.Template;
import templating.extension.NodeVisitor;
import templating.extension.NodeVisitorFactory;


/**
 * Factory class for creating {@link EscaperNodeVisitor}.
 *
 * @author Thomas Hunziker
 *
 */
public class EscaperNodeVisitorFactory implements NodeVisitorFactory {

    private boolean autoEscaping = true;

    @Override
    public NodeVisitor createVisitor(Template template) {
        return new EscaperNodeVisitor(template, this.autoEscaping);
    }

    public void setAutoEscaping(boolean auto) {
        autoEscaping = auto;
    }


}
