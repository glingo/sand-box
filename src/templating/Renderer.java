package templating;

@FunctionalInterface
public interface Renderer {
    
    void render(Template template, Environment environment);
}
