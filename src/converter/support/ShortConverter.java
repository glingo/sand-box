package converter.support;

import converter.Converter;
import java.util.Arrays;
import java.util.List;

/**
 * Convert to a short by parsing the value as a string
 *
 */
public class ShortConverter implements Converter<Object, Short> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(
            Short.class,
            Short.TYPE,
            Short.class.getName()
        );
    }

    @Override
    public Short convert(Object value) {
        
        if (value == null) {
            return null;
        }
        
        if (value instanceof Short) {
            return (Short) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return Short.parseShort(v);
            }
        }
        return null;
    }
}
