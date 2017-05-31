package converter.support;

import converter.Parser;

/**
 * Convert to a long by parsing the value as a string
 *
 */
public class LongParser implements Parser<Long> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[]{
            Long.class,
            Long.TYPE,
            Long.class.getName()
        };
    }

    @Override
    public Long parse(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof Long) {
            return (Long) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return Long.parseLong(v);
            }
        }

        return null;
    }
}
