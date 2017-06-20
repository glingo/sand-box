package templating;

import java.io.File;
import java.util.Optional;
import java.util.regex.Matcher;
import resource.ResourceService;
import resource.loader.ClasspathResourceLoader;
import resource.loader.FileResourceLoader;
import resource.loader.InMemoryResourceLoader;
import resource.loader.StringResourceLoader;
import resource.reference.ResourceReference;

public class TemplatingDemo {
    
    public static void main(String[] args) {
        
        Rule executeStart = Rule.expect("(?<start>\\Q{%\\E)");
        Rule executeEnd = Rule.expect("(?<end>\\Q%}\\E)");
        
        Rule expression = Rule.expect("(?<var>.*)");
        Rule ltrim = Rule.expect("(?<ltrim>\\Q-\\E?+)");
        Rule rtrim = Rule.expect("(?<rtrim>\\Q-\\E?+)");
        
//        Rule execute = Rule.expect("(?<start>\\Q{%\\E)(?<ltrim>\\Q-\\E*)(?<expression>.*)(?<rtrim>\\Q-\\E*)(?<end>\\Q%}\\E)");
        
        Sequence command = Sequence.builder()
                .withRule(executeStart)
                .withRule(ltrim)
                .withRule(expression)
                .withRule(rtrim)
                .withRule(executeEnd)
                .build();
        
        System.out.println(command.validate("{% if true %}"));
        System.out.println(command.groups("{% if true %}"));
        System.out.println(command.groups("{%- if true %}"));
        System.out.println(command.groups("{% %}"));
        System.out.println(command.groups("aze"));
        System.out.println(command.groups("aze"));
        System.out.println(command.validate("{# this is a comment #}"));
        
        
//        ResourceService resourceService = ResourceService.builder()
//                .with(ResourceReference.FILE, FileResourceLoader.instance())
//                .with(ResourceReference.FILE, FileResourceLoader.instance(new File("/Users/florian/Documents/sources/sand-box/demo")))
//                .with(ResourceReference.CLASSPATH, new ClasspathResourceLoader(ClassLoader.getSystemClassLoader()))
//                .with(ResourceReference.MEMORY, InMemoryResourceLoader.builder()
//                        .withResource("test", "Demo in memory String")
//                        .build())
//                .with(ResourceReference.STRING, new StringResourceLoader())
//                .with(ResourceReference.ANY_TYPE, new StringResourceLoader())
//                .build();
//        
//        Environment env = Environment.builder()
//                .resourceService(resourceService)
//                .build();
//        
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
//        Tokenizer tokenizer = Tokenizer.builder()
//                .syntax(syntax)
//                .build();
//        
//        Engine engine = Engine.builder()
//                .environment(env)
//                .tokenizer(tokenizer)
//                .build();
//        
//        Template demo = engine.load("templating/demo.view");
//        
//        Template template = Template.builder().named("first")
//                .build();
//        
//        Template home = Template.builder().named("home")
//                .extend(template)
//                .build();
//        
//        engine.render(home, (t, e) -> {
//            System.out.println(t);
//        });
    }
}
