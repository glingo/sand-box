package converter.support;

import converter.Parser;

/**
 * Convert to a double by parsing the value as a string
 *
 */
public class DoubleParser implements Parser<Double> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[]{
            Double.class,
            Double.TYPE,
            Double.class.getName()
        };
    }

    @Override
    public Double parse(Object value) {
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
