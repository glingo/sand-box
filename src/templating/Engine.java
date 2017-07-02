package templating;

import java.util.List;

public class Engine {

    private Environment environment;

    public Template load(String path) {
        Source source = getEnvironment().load(path);
        List<Token> tokens = getEnvironment().getTokenizer().tokenize(source);

        return Template.builder().tokens(tokens).build();
    }

    public void render(Template template, Renderer renderer) throws Exception {
        renderer.render(template, this.environment);
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

        public EngineBuilder environment(Environment environment) {
            this.environment = environment;
            return this;
        }

        public Engine build() {
            Engine engine = new Engine();

            engine.setEnvironment(environment);

            return engine;
        }

    }
}
