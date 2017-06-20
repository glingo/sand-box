package converter.support;

import converter.Converter;
import java.util.Arrays;
import java.util.List;

/**
 * Convert to a long by parsing the value as a string
 *
 */
public class LongConverter implements Converter<Object, Long> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(
            Long.class,
            Long.TYPE,
            Long.class.getName()
        );
    }

    @Override
    public Long convert(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof Long) {
            return (Long) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return Long.parseLong(v);
            }
        }

        return null;
    }
}
