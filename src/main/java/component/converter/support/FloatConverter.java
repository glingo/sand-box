package component.converter.support;

import component.converter.Converter;
import java.util.Arrays;
import java.util.List;

/**
 * Convert to a float by parsing the value as a string
 *
 */
public class FloatConverter implements Converter<Object, Float> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(
            Float.class,
            Float.TYPE,
            Float.class.getName()
        );
    }

    @Override
    public Float convert(Object value) {
        
        if (value == null) {
            return null;
        }
        
        if (value instanceof Float) {
            return (Float) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return Float.parseFloat(v);
            }
        }
        
        return null;
    }
}
