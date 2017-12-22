package component.expressionLanguage.token.parser;

@FunctionalInterface
public interface Parser<I, R> {

    R parse(I stream);
}
