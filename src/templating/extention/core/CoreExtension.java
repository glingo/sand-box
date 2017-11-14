package templating.extention.core;

import java.util.HashMap;
import java.util.Map;
import templating.Context;
import templating.Renderer;
import templating.extention.Extension;
import templating.node.NodeParser;
import templating.token.Token;
import templating.token.TokenStream;

public class CoreExtension implements Extension {

//    @Override
//    public List<TokenParser> getTokenParsers() {
//        List<TokenParser> parsers = new ArrayList<>();
////        parsers.add(TokenParser.from("ws_trim", "-"));
////        parsers.add(TokenParser.group("execute", "{%", "%}"));
////        parsers.add(TokenParser.group("comment", "{#", "#}"));
////        parsers.add(TokenParser.group("evaluate", "{{", "}}"));
////        parsers.add(TokenParser.from("punctuation", "(", ")", "[", "]", "{", "}", "?", ":", ".", ",", "|", "="));
//        parsers.add(TokenParser.group("execute", "{%", "%}", expression()));
//        parsers.add(TokenParser.group("comment", "{#", "#}"));
//        parsers.add(TokenParser.group("evaluate", "{{", "}}"));
//        return parsers;
//    }
    
//    @Override
//    public Map<String, NodeParser> getNodeParsers() {
//        Map<String, NodeParser> parsers = new HashMap<>();
//        parsers.put("text", test("text"));
//        parsers.put("evaluate", test("evaluate"));
//        parsers.put("execute", test("execute"));
//        parsers.put("comment", test("comment"));
//        parsers.put("ws-trim", test("ws-trim"));
//        return parsers;
//    }

    @Override
    public Map<String, Renderer> getRenderers() {
        Map<String, Renderer> renderers = new HashMap<>();
        renderers.put("text", debug("text"));
        renderers.put("evaluate", debug("evaluate"));
        renderers.put("execute_open", debug("execute_open"));
        renderers.put("execute_close", debug("execute_close"));
        renderers.put("execute", debug("execute"));
        renderers.put("comment", debug("comment"));
        renderers.put("ws-trim", debug("ws-trim"));
        renderers.put("name", debug("name"));
        return renderers;
    }
    
    static Renderer debug(String name) {
        return (Token token, Context context) -> {
            TokenStream stream = context.getStream();
            token = stream.expect(name);
            System.out.println(name + "(" + token.getValue() + ")");
        };
    }
    
//    static NodeParser test(String name) {
//        return (Token token, Context context) -> {
//            TokenStream stream = context.getStream();
//            token = stream.expect(name);
//            System.out.println(name + "(" + token.getValue() + ")");
//        };
//    }
    
//    @Override
//    public Map<String, Renderer> getRenderers() {
//        Map<String, Renderer> renderers = new HashMap<>();
//        renderers.put("text", text());
//        renderers.put("evaluate", debug("evaluate"));
//        renderers.put("execute", debug("execute"));
//        renderers.put("comment", comment());
//        renderers.put("ws-trim", debug("ws-trim"));
//        return renderers;
//    }
//    
//    static Renderer text() {
//        return (Token token, Context context) -> {
//            TokenStream stream = context.getStream();
//            token = stream.expect("text");
//            System.out.println(token.getValue());
//        };
//    }
//    
//    static Renderer comment() {
//        return (Token token, Context context) -> {
//            TokenStream stream = context.getStream();
//            token = stream.expect("comment");
//        };
//    }
//    
//    public static Renderer debug() {
//        return Renderer.builder()
//                .with("text", debug("text"))
//                .with("evaluate", debug("evaluate"))
//                .with("execute", debug("execute"))
//                .with("comment", debug("comment"))
////                .with("evaluate_open", eval())
////                .with("text", text())
////                .with("execute_open", execute())
////                .with("comment_open", comment())
//                .build();
//        return (template, environment) -> {
//            Context context = environment.getContext();
//            TokenStream stream = template.stream();
//            context.setStream(stream);
//            Token token = stream.current();
//            while (!Token.isEOF(token)) {
//                String value = token.getValue();
////                System.out.println("token(" + token.getType() + ")");
//                switch (token.getType()) {
//                    case "evaluate_open":
//                        stream.next();
//                        token = stream.expect("expression");
//                        String expression = token.getValue();
//                        System.out.println("evaluate(" + expression + ")");
//                        // parse evaluation
//                        stream.expect("evaluate_close");
//                        token = stream.next();
//                        break;
//                    case "execute_open":
//                        stream.next();
//                        token = stream.expect("expression");
//                        expression = token.getValue();
//                        System.out.println("execute(" + expression + ")");
//                        token = stream.next();
//                        break;
//                    case "comment_open":
//                        stream.next();
//                        token = stream.expect("expression");
//                        expression = token.getValue();
//                        System.out.println("comment(" + expression + ")");
//                        stream.expect("comment_close");
//                        token = stream.next();
//                        break;
//                    case "print_open":
//                        System.out.print("print(" + value + "): ");
//                        stream.next();
//                        token = stream.expect("expression");
//                        expression = token.getValue();
//                        Optional<Object> evaluated = context.evaluate(expression, Object.class);
//                        if (evaluated.isPresent()) {
//                            System.out.print(evaluated.get());
//                        }
//                        System.out.println(evaluated.get());
//                        token = stream.next();
//                        break;
//                    case "text":
//                        System.out.println("text(" + value + ")");
//                        token = stream.next();
//                        break;
//                    case "expression":
//                        System.out.println("expression(" + value + ")");
//                        token = stream.next();
//                        break;
//                    default:
////                        System.out.println("skip(" + value + ")");
//                        token = stream.next();
//                        break;
//                }
//            }
//        };
//    }
}
