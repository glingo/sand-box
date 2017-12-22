package component.expressionLanguage.extension.core.test;

import component.expressionLanguage.test.Test;
import java.util.Collection;
import java.util.Map;

public class EmptyTest implements Test {

    @Override
    public boolean apply(Object input) {
        boolean isEmpty = input == null;

        if (!isEmpty && input instanceof String) {
            String value = (String) input;
            isEmpty = "".equals(value.trim());
        }

        if (!isEmpty && input instanceof Collection) {
            isEmpty = ((Collection<?>) input).isEmpty();
        }

        if (!isEmpty && input instanceof Map) {
            isEmpty = ((Map<?, ?>) input).isEmpty();
        }

        return isEmpty;
    }

}
