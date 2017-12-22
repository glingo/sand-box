package component.node.legacy;

public class PrototypedArrayNode extends ArrayNode {

    protected Node prototype;
    
    public PrototypedArrayNode(String name) {
        super(name);
    }

    public Node getPrototype() {
        return this.prototype;
    }

    public void setPrototype(Node prototype) {
        this.prototype = prototype;
    }
}
