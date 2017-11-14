package templating;

import templating.token.Token;

@FunctionalInterface
public interface Renderer {
    void render(Token token, Context context) throws Exception;
}
