package node.normalization;

import java.util.HashMap;
import java.util.Map;
import normalizer.Normalization;

public class NodeValueNormalization implements Normalization<Object> {

    @Override
    public Object apply(Object value) {
        if(!(value instanceof Map)) {
            return value;
        }
        
        Map<String, Object> normalized = new HashMap();
        
        Map<String, Object> typedValue = (Map<String, Object>) value;
        typedValue.forEach((String k, Object val) -> {
            
            if(k.contains("-") && !k.contains("_")) {
                String normalizedKey = k.replace("-", "_");
                if(!typedValue.containsKey(normalizedKey)) {
                    k = normalizedKey;
                }
            }
            
            normalized.put(k, val);
        });
        
        return value;
    }
    
}
