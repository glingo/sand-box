package converter.support;

/**
 * Convert to a character by parsing the first character of the value as a
 * string
 *
 */
public class CharacterParser implements Parser<Character> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[]{
            Character.class,
            Character.TYPE,
            Character.class.getName()
        };
    }

    @Override
    public Character parse(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Character) {
            return (Character) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return v.charAt(0);
            }
        }
        
        return null;
    }
}
