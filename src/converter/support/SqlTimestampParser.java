package converter.support;

import converter.Parser;

/**
 * Convert to a {@link SqlTimestamp} by parsing a value as a string of form
 * <code>yyyy-[m]m-[d]d hh:mm:ss[.f...]</code>.
 *
 * @see	java.sql.Date#valueOf(String)
 */
public class SqlTimestampParser implements Parser<java.sql.Timestamp> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[]{
            java.sql.Timestamp.class,
            java.sql.Timestamp.class.getName()
        };
    }

    @Override
    public java.sql.Timestamp parse(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof java.sql.Timestamp) {
            return (java.sql.Timestamp) value;
        } else {
            String v = value.toString();
            if (v.trim().length() == 0) {
                return java.sql.Timestamp.valueOf(v);
            }
        }
        return null;
    }
}
