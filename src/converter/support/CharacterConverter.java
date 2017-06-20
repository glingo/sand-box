package converter.support;

import converter.Converter;
import java.util.Arrays;
import java.util.List;

/**
 * Convert to a character by parsing the first character of the value as a
 * string
 *
 */
public class CharacterConverter implements Converter<Object, Character> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(
            Character.class,
            Character.TYPE,
            Character.class.getName()
        );
    }

    @Override
    public Character convert(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Character) {
            return (Character) value;
        }
        String v = value.toString();
        if (v.trim().length() == 0) {
            return null;
        }
        return v.charAt(0);
    }
}
