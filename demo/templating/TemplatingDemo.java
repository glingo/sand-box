package templating;

import resource.ResourceService;
import resource.loader.ClasspathResourceLoader;
import resource.reference.ResourceReference;
import templating.extention.core.CoreExtension;
import templating.token.Tokenizer;

public class TemplatingDemo {
//    private static final Pattern PATTERN_STRING = compile("((\").*?(?<!\\\\)(\"))|((').*?(?<!\\\\)('))", Pattern.DOTALL);
    
    public static void main(String[] args) throws Exception {
        ResourceService resourceService = ResourceService.builder()
//                .with(ResourceReference.FILE, FileResourceLoader.instance())
//                .with(ResourceReference.FILE, FileResourceLoader.instance(new File("/Users/florian/Documents/sources/sand-box/demo")))
//                .with(ResourceReference.CLASSPATH, new ClasspathResourceLoader(ClassLoader.getSystemClassLoader()))
                .with(ResourceReference.CLASSPATH, new ClasspathResourceLoader(TemplatingDemo.class.getClassLoader()))
                .build();

//        Syntax syntax = Syntax.builder().build();

        Environment env = Environment.builder()
                .resourceService(resourceService)
//                .syntax(syntax)
                .build();

        Engine engine = Engine.builder()
                .environment(env)
                .execute("{%", "%}")
                .comment("{#", "#}")
                .print("{{", "}}")
                .build();

        Template demo = engine.load("templating/demo.view");  
        engine.render(demo);
//        engine.render(demo, Renderer.debug());    
//        engine.render(demo, CoreExtension.debug());   
    }
}
//        engine.render(demo, new StreamRenderer());  
                
//        engine.render(demo, (t, e) -> {
//            t.getTokens().forEach(System.out::println);
//            System.out.println(t);
//        });             
        
//        Template template = Template.builder().named("first")
//                .build();
//        
//        Template home = Template.builder().named("home")
//                .extend(template)
//                .build();
    
//    public static class StreamRenderer implements Renderer {
//        
//        @Override
//        public void render(Template template, Environment environment) throws Exception {
//            Context context = new Context();
//            Writer writer = new OutputStreamWriter(System.out);
//            
//            TokenStream stream = template.stream();
//            Token token = stream.current();
//            
//            while (!Token.isEOF(token)) {
//                switch (token.getType()) {
//                    case "comment_open":
//                        token = stream.expect("comment_open");
//                        token = stream.expect("expression");
//                        token = stream.expect("comment_close");                        
//                        break;
//                    case "evaluate_open":
//                        token = stream.expect("evaluate_open");
//                        token = stream.expect("expression");
//                        token = stream.expect("evaluate_close");     
////                        token = stream.next();
////                        Optional<Object> evaluated = context.evaluate(token.getValue(), Object.class);
////                        if (evaluated.isPresent()) {
////                            writer.write(evaluated.get().toString());
////                        }
//                        break;
//                    case "execute_open":
////                        token = stream.expect("expression");
//                        // evaluate the expression
////                        environment.getSyntax().getExpressions().values().forEach((e) -> {
////                            e.evalute(token.getValue().trim(), context);
////                        });
//                        break;
//                    case "text":
//                        writer.write(token.getValue());
//                        break;
//                    default:
//                        break;
//                }
//                token = stream.next();
//            }
//            
//            writer.flush();
//        }
//    }
//    
//    public static class ForExpression extends BinaryExpression {
//        
//        public ForExpression(String name, String[] operators, BiConsumer consumer) {
//            super(name, operators, consumer);
//        }
//        
//    }

//        Rule executeStart = Rule.group("start", Pattern.quote("{%"));
//        Rule executeEnd = Rule.group("end", Pattern.quote("%}"));
//        
//        Rule expression = Rule.group("var", ".*");
//        Rule ltrim = Rule.group("ltrim", "-", "?+");
//        Rule rtrim = Rule.group("rtrim", "-", "?+");
//        
////        Rule execute = Rule.expect("(?<start>\\Q{%\\E)(?<ltrim>\\Q-\\E*)(?<expression>.*)(?<rtrim>\\Q-\\E*)(?<end>\\Q%}\\E)");
//        
//        Sequence command = Sequence.builder()
//                .withRule(executeStart)
//                .withRule(ltrim)
//                .withRule(expression)
//                .withRule(rtrim)
//                .withRule(executeEnd)
//                .build();
//        
//        System.out.println(command.validate("{% if true %}\nbonjour\n{% endif %}"));
//        System.out.println(command.groups("{% if true %} bonjour {% endif %}"));
//        System.out.println(command.groups("{%- if true %}\nbonjour\n{% endif %}"));
//        System.out.println(command.groups("{% %} {% %}"));
//        System.out.println(command.groups("aze"));
//        System.out.println(command.groups("aze"));
//        System.out.println(command.validate("{# this is a comment #}"));
        

//        Syntax syntax = Syntax.builder()
//                .trim("-")
//                .command("comment", "{#", "#}", (stream, context) -> {})
//                .command("print", "{{", "}}", (stream, context) -> {
//                    Token current = stream.current();
//                    String value = current.getValue();
//                    
//                    Optional<Object> result = context.evaluate(value);
//                    
//                })
//                .command("execute", "{%", "%}", (stream, context) -> {})
//                
//                .expression("if", (value, context) -> {
//                    System.out.println("if");
//                    Optional<Object> result = context.evaluate(value);
//                    
//                    if (result.isPresent()) {
//                        System.out.println(result);
//                    }
//                })
//                .expression("not", (value, context) -> {
//                    System.out.println("not");
//                
//                })
//                .expression("set", (value, context) -> {
//                    System.out.println("set");
//                
//                })
//                .expression("for", "in", (value, context) -> {
//                    System.out.println("for");
//                
//                })
//                .build();
//        



//
//                .expression("if", (String value, Context context) -> {
//                    Optional<Boolean> result = context.evaluate(value, Boolean.class);
//                    if (result.isPresent() && result.get()) {
//                        System.out.println(result.get());
//                    }
//                })
//                .binary("for", new String[]{"in"}, (String value, Context context) -> {
//                    String[] values = value.split("in");
//                    
//                    if (values.length < 2) {
//                        throw new IllegalArgumentException("'for' should be followed by an itteration exression('a in b')");
//                    }
//                    
////                    Token next = context.getStream().next();
//                    
//                    String key = values[0].trim();
//                    String v = values[1].trim();
//                    
//                    // is form : ["", "", "", "", ""] ?
//                    // is form : {a : "", b : ""} ?
//                    
//                    // is a var : a ?
//                    Optional<Object> evaluated = context.evaluate(v, Object.class);
//                    
//                    if (!evaluated.isPresent()) {
//                        return;
//                    }
//                    
//                    Collection list = new ArrayList();
//                    Object o = evaluated.get();
//                    
//                    if (o instanceof Collection) {
//                        list = (Collection) o;
//                    }
//                    
//                    if (o instanceof String) {
//                        char[] chars = ((String) o).toCharArray();
//                        for(char c : chars) {
//                            list.add(c);
//                        }
//                    }
//                    
//                    int i = 0;
//                    for (Object object : list) {
//                        context.getModel().put("index", i++);
//                        context.getModel().put(key, object);
//                        System.out.println(object);
//                    }
//                })
//                .binary("set", new String[]{"="}, (String value, Context context) -> {
//                    String[] values = value.split("=");
//                    
//                    if (values.length < 2) {
//                        throw new IllegalArgumentException("'set' should be followed by a attribution exression('a = b')");
//                    }
//                    
//                    String key = values[0].trim();
//                    String v = values[1].trim().replaceAll("(\\Q\"\\E)", "");
//                    
//                    context.getModel().put(key, v);
//                })