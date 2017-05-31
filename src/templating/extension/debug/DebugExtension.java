package templating.extension.debug;

import templating.extension.AbstractExtension;
import templating.extension.NodeVisitorFactory;
import java.util.ArrayList;
import java.util.List;

public class DebugExtension extends AbstractExtension {

    private final PrettyPrintNodeVisitorFactory prettyPrinter = new PrettyPrintNodeVisitorFactory();

    @Override
    public List<NodeVisitorFactory> getNodeVisitors() {
        List<NodeVisitorFactory> visitors = new ArrayList<>();
        visitors.add(prettyPrinter);
        return visitors;
    }

    @Override
    public String toString() {
        return prettyPrinter.toString();
    }
}
