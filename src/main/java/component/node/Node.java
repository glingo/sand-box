package component.node;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Node {

    protected String name;
    protected String namespace;
    protected Object value;
    protected Object defaultValue;
    protected boolean useDefault;
    protected boolean required;
    protected boolean allowEmptyValue = true;
    protected Map<String, Object> attributes = new HashMap<>();
    
    protected Node parent;
    protected Collection<Node> children;

    public Node() {
        
    }
    
    public Node(String name) {
        this.name = name;
    }
    
    public Node(String namespace, String name, Object value, Map<String, Object> attributes) {
        this.namespace = namespace;
        this.name = name;
        this.value = value;
        this.attributes = attributes;
    }

    public Node(String namespace, String name, Object value) {
        this(namespace, name, value, null);
    }

//    public static NodeBuilder builder() {
//        return new NodeBuilder();
//    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public Collection<Node> getChildren() {
        if (children == null) {
            this.children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(Collection<Node> children) {
        this.children = children;
    }

    public Node attr(String name, Object val) {
        getAttributes().put(name, val);
        return this;
    }
    
    public Node child(Node child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        this.children.add(child);
        child.setParent(this);
        return this;
    }

    public void setDefaultValue(Object defaultValue) {
        this.useDefault = true;
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isAllowEmptyValue() {
        return allowEmptyValue;
    }

    public void setAllowEmptyValue(boolean allowEmptyValue) {
        this.allowEmptyValue = allowEmptyValue;
    }
    
    public Object getValue() {
        if (value instanceof Supplier) {
            return ((Supplier) value).get();
        }
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
    
    public String getNamespace() {
        return namespace;
    }

    public Map getAttributes() {
        if (attributes == null) {
            attributes = new LinkedHashMap<>(3, 1.0f);
        }
        return attributes;
    }

    public void eachChild(Consumer<Node> consumer) {
        getChildren().forEach((child) -> {
            consumer.accept(child);
            child.visit(consumer);
        });
    }

    public Node visit(Consumer<Node> consumer) {
        eachChild(consumer);
        consumer.accept(this);
        return this;
    }

    public Node visitBeforeAndAfter(BiConsumer<Boolean, Node> consumer) {
        consumer.accept(true, this);
        getChildren().forEach((child) -> {
            child.visitBeforeAndAfter(consumer);
        });
        consumer.accept(false, this);
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.attributes);
        hash = 97 * hash + Objects.hashCode(this.value);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.namespace);
        hash = 97 * hash + Objects.hashCode(this.children);
        hash = 97 * hash + Objects.hashCode(this.parent);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.namespace, other.namespace)) {
            return false;
        }
        if (!Objects.equals(this.attributes, other.attributes)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (!Objects.equals(this.children, other.children)) {
            return false;
        }
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Node{" + "attributes=" + attributes + ", value=" + value + ", name=" + name + ", namespace=" + namespace + ", children=" + children + '}';
    }
}
