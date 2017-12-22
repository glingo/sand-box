package component.node.legacy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import component.utils.ObjectUtils;

public class ArrayNode extends Node {

    protected boolean addIfNotSet = false;
    protected boolean normalizeKeys = true;
    protected boolean removeExtraKeys = true;
    protected boolean deepMerging = true;
    protected boolean allowNewKeys = true;
    protected HashMap<String, String> xmlRemapping = new HashMap<>();
    
    public ArrayNode(String name) {
        super(name);
    }

    @Override
    protected Object preNormalize(Object value) {
        
        if(!isNormalizeKeys() || !(value instanceof Map)) {
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
        
        return super.preNormalize(value); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected void validateType(Object value) {
        if(!ObjectUtils.isArray(value) 
                && !(value instanceof Map) 
                && !(value instanceof Set) 
                && !(value instanceof List)) {
            String msg = String.format("Invalid type for path %s. Expected array but got %s", this.getPath(), value == null ? null: value.getClass());
            throw new RuntimeException(msg);
        }
    }

    @Override
    protected Object normalizeValue(Object value) {
        
        HashMap<String, Object> normalized = new HashMap<>();
        
        if(value == null) {
            return normalized;
        }
        
        HashMap<String, Object> casted = (HashMap<String, Object>) value;
        casted = remapXml(casted);
        
        casted.forEach((String key, Object val) -> {
            if(getChildren().containsKey(key)) {
                normalized.put(key, getChildren().get(key).normalizeValue(val));
            } else if (!isRemoveExtraKeys()) {
                normalized.put(key, val);
            }
        });
        
        return normalized;
    }

    @Override
    protected Object mergeValues(Object left, Object right) throws Exception {
        
        if(Boolean.FALSE.equals(right)) {
            return false;
        }
        
        if(Boolean.FALSE.equals(left) || !isDeepMerging()) {
            return right;
        }
        
        return mergeMap((Map) left, (Map) right);
    }
    
    protected Map<String, Object> mergeMap(Map<String, Object> left, Map<String, Object> right) throws Exception{
        for (Map.Entry<String, Object> entrySet : right.entrySet()) {
            String key = entrySet.getKey();
            Object value = entrySet.getValue();
            
            // no conflict
            if(!left.containsKey(key)) {
                if(!isAllowNewKeys()) {
                    String msg = String.format("You are not allowed to define new elements for path '%s'", this.getPath());
                    throw new Exception(msg);
                }
                
                left.put(key, value);
                continue;
            }
            
            if(!getChildren().containsKey(key)){
                throw new Exception("merge() expects a normalized config array.");
            }
            
            left.put(key, getChildren().get(key).merge(left.get(key), value));
        }
        
        return left;
    }

    @Override
    protected Object finalizeValue(Object value) {
        HashMap<String, Object> casted = (HashMap<String, Object>) value;
        
        if(getChildren() != null) {
            for(Map.Entry<String, Node> entry : getChildren().entrySet()) {
                String key = entry.getKey();
                Node child = entry.getValue();

                if(!casted.containsKey(key)) {
                    if(child.isRequired()) {
                        String msg = String.format("The child node '%s' at path '%s' must be configured.", key, this.getPath());
                        throw new RuntimeException(msg);
                    }

                    if(child.hasDefaultValue()) {
                        casted.put(key, child.getDefaultValue());
                    }

                    continue;
                }

                casted.put(key, child.finalize(casted.get(key)));
            }
        }
        
        return casted;
    }
    
    protected HashMap<String, Object> remapXml(HashMap<String, Object> value){
        getXmlRemapping().forEach((String singular, String plural) -> {
            if(value.containsKey(singular)) {
                value.put(plural, value.get(singular));
                value.remove(singular);
            }
        });
        
        return value;
    }
    
    @Override
    public boolean hasDefaultValue() {
        return this.addIfNotSet;
    }

    @Override
    public Object getDefaultValue() {
       
        HashMap<String, Object> defaults = new HashMap<>();
        
        getChildren().forEach((String key, Node child) -> {
            if(child.hasDefaultValue()) {
                defaults.put(key, child.getDefaultValue());
            }
        });
        
        return defaults;
    }

    public void setNormalizeKeys(boolean normalizeKeys) {
        this.normalizeKeys = normalizeKeys;
    }

    public void setXmlRemapping(HashMap<String, String> xmlRemapping) {
        this.xmlRemapping = xmlRemapping;
    }

    public HashMap<String, String> getXmlRemapping() {
        return xmlRemapping;
    }

    public boolean isDeepMerging() {
        return deepMerging;
    }

    public boolean isAddIfNotSet() {
        return addIfNotSet;
    }

    public boolean isAllowNewKeys() {
        return allowNewKeys;
    }

    public boolean isNormalizeKeys() {
        return normalizeKeys;
    }

    public boolean isRemoveExtraKeys() {
        return removeExtraKeys;
    }

    public void setAddIfNotSet(boolean addIfNotSet) {
        this.addIfNotSet = addIfNotSet;
    }

    public void setAllowNewKeys(boolean allowNewKeys) {
        this.allowNewKeys = allowNewKeys;
    }

    public void setDeepMerging(boolean deepMerging) {
        this.deepMerging = deepMerging;
    }

    public void setRemoveExtraKeys(boolean removeExtraKeys) {
        this.removeExtraKeys = removeExtraKeys;
    }
    
}
