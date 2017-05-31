package converter.support;

import converter.Parser;

/**
 * Convert to a float by parsing the value as a string
 *
 */
public class FloatParser implements Parser<Float> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[]{
            Float.class,
            Float.TYPE,
            Float.class.getName()
        };
    }

    @Override
    public Float parse(Object value) {
        
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
