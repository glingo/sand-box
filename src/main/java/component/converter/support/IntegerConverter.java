package component.converter.support;

import component.converter.Converter;
import java.util.Arrays;
import java.util.List;

/**
 * Convert to an integer by parsing the value as a string
 *
 */
public class IntegerConverter implements Converter<Object, Integer> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(
            Integer.class,
            Integer.TYPE,
            Integer.class.getName()
        );
    }

    @Override
    public Integer convert(Object value) {
        
        if (value == null) {
            return null;
        }
        
        if (value instanceof Integer) {
            return (Integer) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return Integer.parseInt(v);
            }
        }
        
        return null;
    }
}
