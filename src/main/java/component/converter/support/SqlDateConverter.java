package component.converter.support;

import component.converter.Converter;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

/**
 * Convert to a {@link SqlDate} by parsing a value as a string of form
 * <code>yyyy-[m]m-[d]d</code>.
 *
 * @see	java.sql.Date#valueOf(String)
 */
public class SqlDateConverter implements Converter<Object, Date> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(
            java.sql.Date.class,
            java.sql.Date.class.getName()
        );
    }

    @Override
    public Date convert(Object value) {
        if (value == null) {
            return null;
        }
        
        if (value instanceof Date) {
            return (Date) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return Date.valueOf(v);
            }
        }
        
        return null;
    }
}
