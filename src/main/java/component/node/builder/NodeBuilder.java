package component.node.builder;

import component.node.Node;
import component.builder.Builder;
import java.util.ArrayList;
import java.util.Collection;

public class NodeBuilder extends Builder<Node> implements NodeBuilderInterface {

    public static final String DEFAULT_NS = "_default_ns";
    
    protected NodeBuilderInterface parent;
    
    protected Collection<NodeBuilderInterface> children;
    
    public NodeBuilder() {
        this(new Node());
    }
    
    public NodeBuilder(Node node) {
        super(node);
    }
    
    public Collection<NodeBuilderInterface> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(Collection<NodeBuilderInterface> children) {
        this.children = children;
    }
    
    public void setParent(NodeBuilderInterface parent) {
        this.parent = parent;
    }

    @Override
    public NodeBuilderInterface end() {
        return parent;
    }
    
    public static NodeBuilder root() {
        return new NodeBuilder(new Node("root"));
    }

    @Override
    public NodeBuilderInterface array(String name) {
        NodeBuilder builder = new NodeBuilder(node(name));
        builder.setParent(this);
        this.getChildren().add(builder);
        return builder;
    }

    @Override
    public NodeBuilderInterface bool(String name) {
        NodeBuilder builder = new NodeBuilder(child(name));
        builder.setParent(this);
        this.getChildren().add(builder);
        return builder;
    }
}
