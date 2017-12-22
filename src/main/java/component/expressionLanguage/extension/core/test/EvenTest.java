package component.expressionLanguage.extension.core.test;

import component.expressionLanguage.test.Test;

public class EvenTest implements Test {

    @Override
    public boolean apply(Object input) {
        if (input == null) {
            throw new IllegalArgumentException("Can not pass null value to \"even\" test.");
        }

        if (input instanceof Integer) {
            return ((Integer) input) % 2 == 0;
        } else {
            return ((Long) input) % 2 == 0;
        }
    }
}
