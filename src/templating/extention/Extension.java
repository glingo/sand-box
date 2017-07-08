package templating.extention;

import java.util.List;
import java.util.Map;

public interface Extension {
    
    default List<Test> getTests() {
        return null;
    }
    
    default List<Function> getFunctions() {
        return null;
    }
    
    default List<Filter> getFilters() {
        return null;
    }
    
    default Map<String, TokenParser> getTokenParsers() {
        return null;
    }
}
