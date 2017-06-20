package templating;

@FunctionalInterface
public interface TokenParser {
    Token parse(Source source);
}
