package component.node.legacy;

import java.util.function.Supplier;
import component.utils.ObjectUtils;

public class VariableNode extends Node {

    protected boolean defaultValueSet = false;
    protected boolean allowEmptyValue = true;
    protected Object defaultValue;
    
    public VariableNode(String name) {
        super(name);
    }

    public VariableNode(String name, Node parent) {
        super(name, parent);
    }

    @Override
    public boolean hasDefaultValue() {
        return this.defaultValueSet;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValueSet = true;
        this.defaultValue = defaultValue;
    }

    @Override
    public Object getDefaultValue() {
        Object v = defaultValue;
        
        if(v instanceof Supplier) {
            v = ((Supplier) defaultValue).get();
        }
        
        return v;
    }

    @Override
    protected void validateType(Object value) {
        // do nothing
    }

    @Override
    protected Object normalizeValue(Object value) {
        return value;
    }

    @Override
    protected Object mergeValues(Object left, Object right) {
        return right;
    }

    @Override
    protected Object finalizeValue(Object value) {
        
        if(!isAllowEmptyValue() && ObjectUtils.isEmpty(value)) {
            String msg = String.format("The path '%s' cannot contain an empty value, but got %s.", this.getPath(), value);
            throw new RuntimeException(msg);
        }
        
        return value;
    }

    public boolean isAllowEmptyValue() {
        return allowEmptyValue;
    }

    public void setAllowEmptyValue(boolean allowEmptyValue) {
        this.allowEmptyValue = allowEmptyValue;
    }
}
