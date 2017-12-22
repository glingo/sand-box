package component.converter.support;

import component.converter.Converter;
import java.util.Arrays;
import java.util.List;

/**
 * Converts the value to a string. If the value is a byte or char array, it is
 * converted to a string via {@link toString()}.
 *
 */
public class StringConverter implements Converter<Object, String> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(
            String.class,
            String.class.getName(),
            "string",
            "String"
        );
    }

    @Override
    public String convert(Object value) {
        if (value == null) {
            return null;
        }

        if (value.getClass().isArray()) {
            // This is a byte array; we can convert it to a string
            if (value.getClass().getComponentType() == Byte.TYPE) {
                return new String((byte[]) value);
            }
            
            if (value.getClass().getComponentType() == Character.TYPE) {
                return new String((char[]) value);
            }
        } else if (!(value instanceof String)) {
            return value.toString();
        } else {
            return (String) value;
        }

        return null;
    }
}
