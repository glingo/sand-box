package templating.extention;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import templating.Renderer;
import templating.node.NodeParser;
import templating.token.TokenParser;

public interface Extension {
    
    default List<Test> getTests() {
        return new ArrayList<>();
    }
    
    default List<Function> getFunctions() {
        return new ArrayList<>();
    }
    
    default List<Filter> getFilters() {
        return new ArrayList<>();
    }
    
    default List<TokenParser> getTokenParsers() {
        return new ArrayList<>();
    }
    
    default Map<String, NodeParser> getNodeParsers() {
        return new HashMap<>();
    }
    
    default Map<String, Renderer> getRenderers() {
        return new HashMap<>();
    }
}
