package component.converter.support;

import component.converter.Converter;
import java.util.Arrays;
import java.util.List;

/**
 * Convert to a boolean by parsing the value as a string
 */
public class BooleanConverter implements Converter<Object, Boolean> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(Boolean.class,
            Boolean.TYPE,
            Boolean.class.getName());
    }

    @Override
    public Boolean convert(Object value) {
        
        if (value == null) {
            return null;
        }
        
        if(value instanceof Boolean) {
            return (Boolean) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return Boolean.parseBoolean(v);
            }
        }
        
        return null;
    }
}
