package component.expressionLanguage.extension.core.test;

import component.expressionLanguage.test.Test;
import java.util.Map;

public class MapTest implements Test {

    @Override
    public boolean apply(Object input) {
        return input instanceof Map;
    }

}
