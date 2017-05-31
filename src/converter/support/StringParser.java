package converter.support;

import converter.Parser;

/**
 * Converts the value to a string. If the value is a byte or char array, it is
 * converted to a string via {@link toString()}.
 *
 */
public class StringParser implements Parser<String> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[]{
            String.class,
            String.class.getName(),
            "string",
            "String"
        };
    }

    @Override
    public String parse(Object value) {
        if (value == null) {
            return null;
        }

        if (value.getClass().isArray()) {
            // This is a byte array; we can convert it to a string
            if (value.getClass().getComponentType() == Byte.TYPE) {
                return new String((byte[]) value);
            } else if (value.getClass().getComponentType() == Character.TYPE) {
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
