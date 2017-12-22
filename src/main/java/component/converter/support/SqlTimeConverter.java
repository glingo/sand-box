package component.converter.support;

import component.converter.Converter;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;

/**
 * Convert to a {@link SqlTime} by parsing a value as a string of form
 * <code>hh:mm:ss</code>.
 *
 * @see	java.sql.Date#valueOf(String)
 */
public class SqlTimeConverter implements Converter<Object, Time> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(
            Time.class,
            Time.class.getName()
        );
    }

    @Override
    public Time convert(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Time) {
            return (Time) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return Time.valueOf(v);
            }
        }
        return null;
    }
}
