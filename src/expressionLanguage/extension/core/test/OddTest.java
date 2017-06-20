package expressionLanguage.extension.core.test;

import expressionLanguage.test.Test;

public class OddTest implements Test {

    @Override
    public boolean apply(Object input) {
        if (input == null) {
            throw new IllegalArgumentException("Can not pass null value to \"odd\" test.");
        }
        EvenTest evenTest = new EvenTest();
        return !evenTest.apply(input);
    }
}
