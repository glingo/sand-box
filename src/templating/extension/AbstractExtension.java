package templating.extension;

import templating.operator.BinaryOperator;
import templating.operator.UnaryOperator;
import templating.tokenParser.TokenParser;
import java.util.List;
import java.util.Map;

public abstract class AbstractExtension implements Extension {

    @Override
    public List<TokenParser> getTokenParsers() {
        return null;
    }

    @Override
    public List<BinaryOperator> getBinaryOperators() {
        return null;
    }

    @Override
    public List<UnaryOperator> getUnaryOperators() {
        return null;
    }

    @Override
    public Map<String, Filter> getFilters() {
        return null;
    }

    @Override
    public Map<String, Test> getTests() {
        return null;
    }

    @Override
    public Map<String, Function> getFunctions() {
        return null;
    }

    @Override
    public Map<String, Object> getGlobalVariables() {
        return null;
    }

    @Override
    public List<NodeVisitorFactory> getNodeVisitors() {
        return null;
    }
}
