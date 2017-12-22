package component;



import component.node.builder.NodeBuilder;
import component.node.Node;
import component.builder.Builder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test {
    
    public static void main(String[] args) {
        
        String input = "FLORIAN\n" +
                        "Help Me!\n" +
                        "Date:16/11/2017\n" +
                        "Time:15:40:23\n" +
                        "Speed:0Km/h\n" +
                        "Altitude:189.0m\n" +
                        "Bat:78%\n" +
                        "maps.google.com/maps?g=43.301228,-0.371803";
//        (?<msg>Help Me\\Q!\\E$)(?<date>\\QDate:\\E[0-9]{2}/[0-9]{2}/[0-9]{4}$)(?<autre>.+)$
        String regex = "^(?<contract>\\w+$)";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(input);
        
        System.out.println("Matcher stats");
        System.out.print("Matches : ");
        System.out.println(matcher.matches());
        System.out.print("Loking at : ");
        System.out.println(matcher.lookingAt());
        System.out.print("Group count : ");
        System.out.println(matcher.groupCount());
        System.out.println();
        
        if (matcher.lookingAt()) {
            
        }
        
        while (matcher.find()) {
            System.out.println("contract="+matcher.group("contract"));
            System.out.println("msg="+matcher.group("msg"));
            System.out.println("date="+matcher.group("date"));
            System.out.println("autre="+matcher.group("autre"));
            System.out.println("result="+matcher.group("result"));
            System.out.println(matcher.groupCount());
        }

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
