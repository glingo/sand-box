package expressionLanguage.extension.core.test;

import expressionLanguage.test.Test;

public class IterableTest implements Test {

    @Override
    public boolean apply(Object input) {
        return input instanceof Iterable;
    }
}
