package converter.support;

import converter.Converter;
import java.util.Arrays;
import java.util.List;

/**
 * Returns the value as-is (no conversion)
 *
 */
public class IdentityConverter implements Converter<Object, Object> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList();
    }

    @Override
    public Object convert(Object value) {
        return value;
    }
}
