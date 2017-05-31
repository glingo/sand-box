package templating.extension.escaper;

import templating.extension.Filter;
import java.util.List;
import java.util.Map;

public class RawFilter implements Filter {

    @Override
    public List<String> getArgumentNames() {
        return null;
    }

    @Override
    public Object apply(Object inputObject, Map<String, Object> args) {
        if(inputObject instanceof String){
            return new SafeString((String) inputObject);
        }
        return inputObject;
    }

}
