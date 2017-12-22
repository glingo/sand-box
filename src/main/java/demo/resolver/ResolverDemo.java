package demo.resolver;

import component.resolver.Resolver;
import component.resolver.ResolverDelegate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import component.resource.reference.ResourceReference;

public class ResolverDemo {
    
    public static void main(String[] args) {
        greet();
        resource();
        delegate();
    }
    
    public static void greet() {
        System.out.println("=== GREET ===");
        greet1();
    }
    
    public static void resource() {
        System.out.println("=== Resource ===");
        resource1();
    }    
    
    public static class Greeter {
        
        private final Pattern pattern = Pattern.compile("\\{.*?\\}");
        
        public Greeter() {
        }
        
        public void greet(String name, String lang) {
            this.model.put("name", name);
            Locale locale = new LocaleResolver().resolve(lang).get();
            if (locale.equals(Locale.FRANCE)) {
                System.out.println(evaluate("Bonjour {name} !", model));
            } else if (locale.equals(Locale.ENGLISH)) {
                System.out.println(evaluate("Hello {name} !", model));
            }
        }
        
        private Map<String, Object> model = new HashMap<>();
        private String evaluate(String subject, Map<String, Object> model) {
            Matcher matcher = this.pattern.matcher(subject);
            StringBuffer sb = new StringBuffer();
        
            while (matcher.find()) {
                String key = matcher.group().replaceAll("[{}]", "");
                if (this.model.containsKey(key)) {
                    matcher.appendReplacement(sb, Objects.toString(this.model.get(key), "null"));
                }
            }
        
            matcher.appendTail(sb);
            
            return sb.toString();
        }

    };
    
    public static class LocaleResolver implements Resolver<String, Locale> {
        
        public LocaleResolver() {
        }
        
        @Override
        public Optional<Locale> resolve(String locale) {
            switch(locale) {
                case "fr" :
                    return Optional.of(Locale.FRANCE);
                case "en" :
                    return Optional.of(Locale.ENGLISH);
                default :
                    return Optional.of(Locale.getDefault());
            }
        }
    }
    
    public static void greet1() {
        System.out.println("=== GREET 1 ===");
        Greeter greeter = new Greeter();
        greeter.greet("world", "fr");
    }
    
    public static void resource1() {
        System.out.println("=== RESOURCE 2 ===");
        
        Resolver<String, ResourceReference> resolver = (String path) -> Optional.of(ResourceReference.file(path));
        
        System.out.println(resolver.resolve("/").get());
    }
    
    public static void delegate() {
        System.out.println("=== delegate ===");
        
        ResolverDelegate<String, Object> delegate = new ResolverDelegate<>();
        
        delegate.addResolver((s) -> s.startsWith("/"), (String path) -> Optional.of(path + " ../"));
        delegate.addResolver((s) -> s.startsWith("test"), (String path) -> Optional.of("Ceci est un test"));
        
        System.out.println(delegate.resolve("/").get());
        System.out.println(delegate.resolve("test").get());
        
        Resolver<String, ResourceReference> resolver = (String path) -> {
            return Optional.of(new ResourceReference("file", path));
        };
        
        System.out.println(resolver.resolve("/").get());
        System.out.println(resolver.resolve("test").get());
    }
    }
