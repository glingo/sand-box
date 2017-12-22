package component.node.legacy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

public abstract class Node {
    
    protected String name;
    protected Node parent;
    protected boolean required;
    protected boolean allowOverWrite = true;
    protected boolean useDefault = true;
    protected Object defaultValue;
    protected HashMap<String, Object> attributes;
    
    protected LinkedHashMap<String, Node> children;
    
    protected List<Function<Object, Object>> normalizationClosures;
    protected List<Function<Object, Object>> finalValidationClosures;
    
    public Node(String name) {
        this.name = name;
    }

    public Node(String name, Node parent) {
        this.name = name;
        this.parent = parent;
    }
    
    protected Object preNormalize(Object value) {
        return value;
    }
    
    final public Object normalize(Object value) {
        
        value = preNormalize(value);
        
        if(getNormalizationClosures() != null) {
            for (Function<Object, Object> closure : getNormalizationClosures()) {
                value = closure.apply(value);
            }
        }
        
        // replaces with equivalent values
        // dans un tableau de valeur
        
        validateType(value);
        
        return normalizeValue(value);
    }
    
    final public Object merge(Object left, Object right) throws Exception {
        
        if(!isAllowOverWrite()) {
            String msg = String.format("Configuration path %s, can not be overwritten. "
                    + "You have to define all options for this path, "
                    + "and any of its sub-paths in one configuration section.", this.getPath());
            throw new Exception(msg);
        }
        
        // validate type of left side object
        validateType(left);
        
        // validate type of right side object
        validateType(right);
        
        return mergeValues(left, right);
    }
    
    final public Object finalize(Object value) {
        
        validateType(value);
        
        value = finalizeValue(value);
        
        if(getFinalValidationClosures() != null) {
            for (Function<Object, Object> closure : getFinalValidationClosures()) {
                value = closure.apply(value);
            }
        }
        
        return value;
    }
    
    abstract protected void validateType(Object value);
    
    abstract protected Object normalizeValue(Object value);
    
    abstract protected Object mergeValues(Object left, Object right) throws Exception;

    abstract protected Object finalizeValue(Object value);
    
    abstract public boolean hasDefaultValue();
    
    abstract public Object getDefaultValue();
    
    public void addChild(Node child){
        
        if(getChildren() == null) {
            setChildren(new LinkedHashMap<>());
        }
        
        getChildren().putIfAbsent(child.getName(), child);
        child.setParent(this);
    }
    
    public final String getPath() {
        String path = getName();
        
        if(getParent() != null) {
            path = getParent().getName() + "." + path;
        }
        
        return path;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public HashMap<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    public boolean isAllowOverWrite() {
        return this.allowOverWrite;
    }
    
    public void setAllowOverWrite(boolean allowOverWrite) {
        this.allowOverWrite = allowOverWrite;
    }

    public List<Function<Object, Object>> getFinalValidationClosures() {
        return this.finalValidationClosures;
    }

    public List<Function<Object, Object>> getNormalizationClosures() {
        return this.normalizationClosures;
    }

    public void setFinalValidationClosures(List<Function<Object, Object>> finalValidationClosures) {
        this.finalValidationClosures = finalValidationClosures;
    }

    public void setNormalizationClosures(List<Function<Object, Object>> normalizationClosures) {
        this.normalizationClosures = normalizationClosures;
    }

    public void setChildren(LinkedHashMap<String, Node> children) {
        this.children = children;
    }

    public LinkedHashMap<String, Node> getChildren() {
        return this.children;
    }
    
    private int getRank(){
        int rank = 0;
        
        Node current = this;
        
        while(current.parent != null) {
            rank++;
            current = current.parent;
        }
        
        return rank;
    }

//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        
//        int rank = this.getRank();
//        builder.append("|");
//        
//        for (int i = 0; i < rank; i++) {
//            builder.append("-----");
//        }
//        
//        builder.append(this.name);
//        builder.append("\n");
//        
//        if(this.children != null) {
//            this.children.values().forEach((Node child) -> {
//                builder.append(child);
//            });
//        }
//        
//        return builder.toString();
//    }
    
}
