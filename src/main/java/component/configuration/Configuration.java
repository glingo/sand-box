package component.configuration;

import component.node.builder.NodeBuilder;

public class Configuration {
    public static NodeBuilder builder() {
        return new NodeBuilder();
    }
}
