

import node.builder.NodeBuilder;
import node.Node;
import builder.Builder;


public class Test {
    
    public static void main(String[] args) {
        
//        
//        Node node = Builder.of(Node::new).consume(
//            n -> n.attr("info", "Informations")
//        ).consume((n) -> {
//            System.out.append("visiting");
//        }).build();
//        
//        node = NodeBuilder.root()
//                .info("Root informations")
//                .attribute("attribute", 17)
//                .array("child - 1")
//                    .info("child - 1 informations")
//                    .array("child - 1 - 2")
//                        .info("child - 1 - 2 informations")
//                    .end()
//                .end()
//                .bool("boolean")
//                    .info("Information")
//                    .defaultTrue()
//                .end()
//                .array("child - 2")
//                    .info("child - 2 informations")
//                .end()
//                .build();
//        
//        System.out.append("" + node);
//        
//        new TreeNodeBuilder()
//                .root("root")
//                    .info("Root informations")
//                    .attribute("attribute", 17)
//                .child("child")
//                    .info("child informations")
//                .end()
//                .bool("boolean")
//                    .info("Information")
//                    .defaultTrue()
//                .end()
//                .array("root")
//                    .info("root informations")
//                    .child();

//        Builder.of(Node::new)
//            .before((product, builder) -> {
//                System.out.println(Builder.BEFORE);
//                System.out.println(product);
//            })
//            .with((product) -> {
//                System.out.println(Builder.WITH);
//                product.child(new Node("1", "1", "1"));
//                System.out.println(product);
//            })
//            .with((product) -> {
//                System.out.println(Builder.WITH);
//                product.child(new Node("2", "2", "2"));
//                System.out.println(product);
//            })
//            .after((product, builder) -> {
//                System.out.println(Builder.AFTER);
//                System.out.println(product);
//            })
//            .build();
//            .child(new Node())
//            .visit((node) -> System.out.println(node));

//            new NodeBuilder().info(info)
//                    .child("services")
                    
                
//        Node.builder()
//                .child(new Node())
//                .visit((node) -> System.out.println(node));

//            .arrayNode("router")
//                .info("router configuration")
//                .scalarNode("resource")
//                    .info("Resource path")
//                    .required()
//                .end()
//            .end()
                    
//          Configruation.builder().root()
//                .array("parameters")
//                    .info("")
//                    .scalar("resource")
//                        .info("Resource path")
//                        .required()
//                    .end()
//                .end();
    }
}
