package converter.support;

import converter.Parser;

/**
 * Convert to a {@link SqlTime} by parsing a value as a string of form
 * <code>hh:mm:ss</code>.
 *
 * @see	java.sql.Date#valueOf(String)
 */
public class SqlTimeParser implements Parser<java.sql.Time> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[]{
            java.sql.Time.class,
            java.sql.Time.class.getName()
        };
    }

    @Override
    public java.sql.Time parse(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof java.sql.Time) {
            return (java.sql.Time) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return java.sql.Time.valueOf(v);
            }
        }
        return null;
    }
}
