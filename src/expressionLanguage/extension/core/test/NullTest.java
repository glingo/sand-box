package expressionLanguage.extension.core.test;

import expressionLanguage.test.Test;

public class NullTest implements Test {

    @Override
    public boolean apply(Object input) {
        return input == null;
    }
}
