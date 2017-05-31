package node.legacy;

public class EnumNode extends ScalarNode {

    protected Object[] values;
    
    public EnumNode(String name) {
        super(name);
    }
    
    public EnumNode(String name, Object[] values) {
        super(name);
        this.values = values;
    }
    
}
