package node.legacy;

public abstract class NumericNode extends ScalarNode {

    public NumericNode(String name) {
        super(name);
    }
    
    public NumericNode(String name, Node parent) {
        super(name, parent);
    }
    
}
