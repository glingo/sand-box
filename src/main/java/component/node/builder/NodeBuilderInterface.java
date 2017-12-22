package component.node.builder;

import component.node.Node;
import component.builder.BuilderInterface;
import java.util.function.Consumer;

public interface NodeBuilderInterface extends BuilderInterface<Node> {
    
    public NodeBuilderInterface array(String name);
    public NodeBuilderInterface bool(String name);
    public NodeBuilderInterface end();
    
    default Node child(String name) {
        return child(node(name));
    }
    
    default Node child(Node node) {
        consume((parent) -> {
            parent.child(node);
        });
        return node;
    }
    
    default Node node(String name) {
        return new Node(name);
    }
    
    default NodeBuilderInterface name(String name) {
        consume((node) -> {
            node.setName(name);
        });
        return this;
    }
    
    default NodeBuilderInterface visit(Consumer<Node> consumer) {
        consume((node) -> {
            node.visit(consumer);
        });
        return this;
    }
    
    default NodeBuilderInterface namespace(String namespace) {
        consume((node) -> {
            node.setNamespace(namespace);
        });
        return this;
    }
    
    default NodeBuilderInterface attribute(String key, Object attribute) {
        consume((node) -> {
            node.attr(key, attribute);
        });
        return this;
    }
    
    default NodeBuilderInterface defaultValue(Object value) {
        consume((node) -> {
            node.setDefaultValue(value);
        });
        return this;
    }
    
    default NodeBuilderInterface defaultNull() {
        return defaultValue(null);
    }
    
    default NodeBuilderInterface defaultTrue() {
        return defaultValue(true);
    }

    default NodeBuilderInterface defaultFalse() {
        return defaultValue(false);
    }
    
    default NodeBuilderInterface required() {
        consume((node) -> {
            node.setRequired(true);
        });
        return this;
    }
    
    default NodeBuilderInterface cannotBeEmpty() {
        consume((node) -> {
            node.setAllowEmptyValue(false);
        });
        return this;
    }
    
    default NodeBuilderInterface info(String info) {
        return attribute("info", info);
    }
}
