package converter.support;

import converter.Parser;

/**
 * Convert to a {@link SqlDate} by parsing a value as a string of form
 * <code>yyyy-[m]m-[d]d</code>.
 *
 * @see	java.sql.Date#valueOf(String)
 */
public class SqlDateParser implements Parser<java.sql.Date> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[]{
            java.sql.Date.class,
            java.sql.Date.class.getName()
        };
    }

    @Override
    public java.sql.Date parse(Object value) {
        if (value == null) {
            return null;
        }
        
        if (value instanceof java.sql.Date) {
            return (java.sql.Date) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return java.sql.Date.valueOf(v);
            }
        }
        
        return null;
    }
}
