package templating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import utils.StringUtils;

public class Engine {

    private Environment environment;

    private Tokenizer tokenizer;

    private Map<String, Expression> expressions;

    public Template load(String path) {
        Source source = getEnvironment().load(path);
        List<Token> tokens = this.tokenizer.tokenize(source);
        Context context = new Context();
        TokenStream stream = new TokenStream(tokens, path);

        Token token = stream.current();
        while (!Token.isEOF(token)) {
//            System.out.println("Reading Token : " + token);
            switch (token.getType()) {
                case "comment":
                    System.out.println("// " + token.getValue());
                    token = stream.next();
                    break;
                case "execute":
                    // evaluate the expression
                    String value = token.getValue();
                    getExpressions().forEach((n, e) -> {
                        Matcher m = e.getPattern().matcher(value);
                        if (m.find()) {
                            String name = m.group("name");
                            String expression = m.group("expression");

                            e.getConsumer().accept(expression, context);

                            System.out.println(String.format("Expression : %s %s", name, expression));
                            if (m.groupCount() > 2) {
                                String operator = m.group("operator");
                                String nested = m.group("nested");
                                System.out.println(String.format("%s %s", operator, nested));
                            }
                        }
                    });
                    token = stream.next();
                    break;
                default:
                    token = stream.next();
                    break;
            }

        }

//        stream.getTokens().forEach(System.out::println);
        return Template.builder().tokens(stream.getTokens()).build();
    }

    public void render(Template template, Renderer renderer) {
        renderer.render(template, this.environment);

//        stream.getTokens().forEach(System.out::println);
    }

    public Map<String, Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(Map<String, Expression> expressions) {
        this.expressions = expressions;
    }

    public Tokenizer getTokenizer() {
        return tokenizer;
    }

    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public static EngineBuilder builder() {
        return new EngineBuilder();
    }

    public static class EngineBuilder {

        private Environment environment;

        private Tokenizer tokenizer;
        private Map<String, Expression> expressions = new HashMap<>();

        public EngineBuilder environment(Environment environment) {
            this.environment = environment;
            return this;
        }

        public EngineBuilder tokenizer(Tokenizer tokenizer) {
            this.tokenizer = tokenizer;
            return this;
        }

        public EngineBuilder expression(String name, BiConsumer<String, Context> consumer) {
            Expression expression = new Expression();
            expression.setConsumer(consumer);
            expression.setPattern(Pattern.compile(buildExpression(name, "")));
            expressions.put(name, expression);
            return this;
        }

        public EngineBuilder expression(String name, String operator, BiConsumer<String, Context> consumer) {
            Expression expression = new Expression();
            expression.setConsumer(consumer);
            expression.setPattern(Pattern.compile(buildExpression(name, operator)));
            expressions.put(name, expression);
            return this;
        }

        // while .
        // if .
        // not .
        // is .
        // for . in .
        private String buildExpression(String name, String operator) {
            String base = "^"
                    .concat("(?<name>").concat(Pattern.quote(name)).concat(")")
                    .concat("(?<expression>.*)");

            if (StringUtils.hasText(operator)) {
                base = base
                        .concat("(?<operator>").concat(Pattern.quote(operator)).concat("?+)")
                        .concat("(?<nested>.*)");
            }

            return base;
        }

        public Engine build() {
            Engine engine = new Engine();

            engine.setTokenizer(tokenizer);
            engine.setEnvironment(environment);
            engine.setExpressions(expressions);

            return engine;
        }

    }
}
