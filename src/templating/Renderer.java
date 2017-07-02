package templating;

import java.util.Optional;

@FunctionalInterface
public interface Renderer {
    
    void render(Template template, Environment environment) throws Exception;
    
    static Renderer debug() {
        Context context = new Context();
        
        return (template, environment) -> {
            TokenStream stream = template.stream();
            context.setStream(stream);
            Token token = stream.current();
            while (!Token.isEOF(token)) {
                String value = token.getValue();
                switch (token.getType()) {
                    case "comment":
                        System.out.println("comment(" + value + ")");
                        token = stream.next();
                        break;
                    case "evaluate":
                        System.out.print("evaluate(" + value + "): ");
                        Optional<Object> evaluated = context.evaluate(value, Object.class);
                        if (evaluated.isPresent()) {
                            System.out.print(evaluated.get());
                        }
                        System.out.println();
                        token = stream.next();
                        break;
                    case "execute":
                        System.out.println("execute(" + value + ")");
                        environment.getSyntax().getExpressions().values().forEach((e) -> {
                            e.evalute(value.trim(), context);
                        });
                        token = stream.next();
                        break;
                    case "text":
                        System.out.println("text(" + value + ")");
                        token = stream.next();
                        break;
                    default:
                        System.out.println("skip(" + value + ")");
                        token = stream.next();
                        break;
                }
            }
        };
    }
}
