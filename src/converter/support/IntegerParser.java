package converter.support;

import converter.Parser;
import java.util.Arrays;

/**
 * Convert to an integer by parsing the value as a string
 *
 */
public class IntegerParser implements Parser<Integer> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[]{
            Integer.class,
            Integer.TYPE,
            Integer.class.getName()
        };
    }

    @Override
    public Integer parse(Object value) {
        
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
