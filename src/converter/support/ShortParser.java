package converter.support;

import converter.Parser;

/**
 * Convert to a short by parsing the value as a string
 *
 */
public class ShortParser implements Parser<Short> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[]{
            Short.class,
            Short.TYPE,
            Short.class.getName()
        };
    }

    @Override
    public Short parse(Object value) {
        
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
