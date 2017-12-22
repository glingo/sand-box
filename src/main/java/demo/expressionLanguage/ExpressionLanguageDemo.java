package demo.expressionLanguage;

import component.expressionLanguage.Engine;
import component.expressionLanguage.extension.ExtensionRegistry;
import component.expressionLanguage.extension.core.CoreExtension;
import component.expressionLanguage.lexer.Lexer;
import component.expressionLanguage.lexer.Syntax;
import component.expressionLanguage.model.template.Template;
import component.expressionLanguage.token.parser.Parser;
import component.expressionLanguage.token.parser.TokenStreamParser;
import java.io.File;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import component.resource.ResourceService;
import component.resource.loader.ClasspathResourceLoader;
import component.resource.loader.FileResourceLoader;
import component.resource.loader.InMemoryResourceLoader;
import component.resource.loader.StringResourceLoader;
import component.resource.reference.ResourceReference;

public class ExpressionLanguageDemo {
    
    public static void main(String[] args) throws Exception {
        ResourceService resourceService = ResourceService.builder()
                .with(ResourceReference.FILE, FileResourceLoader.instance())
                .with(ResourceReference.FILE, FileResourceLoader.instance(new File("/Users/florian/Documents/sources/sand-box/demo")))
                .with(ResourceReference.CLASSPATH, new ClasspathResourceLoader(ClassLoader.getSystemClassLoader()))
                .with(ResourceReference.MEMORY, InMemoryResourceLoader.builder()
                        .withResource("test", "Demo in memory String")
                        .build())
                .with(ResourceReference.STRING, new StringResourceLoader())
                .with(ResourceReference.ANY_TYPE, new StringResourceLoader())
                .build();
        
        Syntax syntax = Syntax.builder()
                .comment("{#", "#}")
                .execute("{%", "%}")
                .print("{{", "}}")
                .wsTrim("-")
                .build();
        
        ExtensionRegistry registry = ExtensionRegistry.builder()
                .with(new CoreExtension())
                .build();
        
        Lexer lexer = Lexer.builder()
                .syntax(syntax)
                .operators(registry.getOperators())
                .build();
        
        Parser parser = new TokenStreamParser(registry.getOperators(), registry.getTokenParsers());
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Engine engine = new Engine(resourceService, lexer, parser, registry, true, Locale.getDefault(), executorService);
        
        Template template = engine.load("expressionLanguage/demo");
        
        
        System.out.println(template);
//        Lexer lexer = new Lexer(syntax, registry.getUnaryOperators(), registry.getBinaryOperators());
       
//        Parser parser,
//        ExtensionRegistry extensionRegistry,
//        boolean strictVariables, 
//        Locale defaultLocale, 
//        ExecutorService executorService
//        Collection<UnaryOperator> unaries = Arrays.asList();
//        Collection<BinaryOperator> binaries = Arrays.asList();
//        Lexer lexer = new Lexer(),
//        Parser parser,
//        ExtensionRegistry extensionRegistry,
//        boolean strictVariables, 
//        Locale defaultLocale, 
//        ExecutorService executorService
        
//        Engine engine = new Engine();
    }
    
}
