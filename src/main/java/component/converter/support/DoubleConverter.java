package component.converter.support;

import component.converter.Converter;
import java.util.Arrays;
import java.util.List;

/**
 * Convert to a double by parsing the value as a string
 *
 */
public class DoubleConverter implements Converter<Object, Double> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(
            Double.class,
            Double.TYPE,
            Double.class.getName()
        );
    }

    @Override
    public Double convert(Object value) {
        if (value == null) {
            return null;
        }
        
        if (value instanceof Double) {
            return (Double) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return Double.parseDouble(v);
            }
        }
        return null;
    }
}
