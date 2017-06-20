package converter.support;

import converter.Converter;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

/**
 * Convert to a {@link SqlTimestamp} by parsing a value as a string of form
 * <code>yyyy-[m]m-[d]d hh:mm:ss[.f...]</code>.
 *
 * @see	java.sql.Date#valueOf(String)
 */
public class SqlTimestampConverter implements Converter<Object, Timestamp> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(
            Timestamp.class,
            Timestamp.class.getName()
        );
    }

    @Override
    public Timestamp convert(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Timestamp) {
            return (Timestamp) value;
        } else {
            String v = value.toString();
            if (v.trim().length() == 0) {
                return Timestamp.valueOf(v);
            }
        }
        return null;
    }
}
