package component.converter.support;

import component.converter.Converter;
import java.util.Arrays;
import java.util.List;

/**
 * Convert to a byte by parsing the value as a string
 */
public class ByteConverter implements Converter<Object, Byte> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(Byte.class,
            Byte.TYPE,
            Byte.class.getName());
    }

    @Override
    public Byte convert(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Byte) {
            return (Byte) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return Byte.parseByte(v);
            }
        }

        return null;
    }
}
