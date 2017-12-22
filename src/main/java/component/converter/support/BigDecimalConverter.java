package component.converter.support;

import component.converter.Converter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Convert to a {@link BigDecimal} by parsing the value as a string
 */
public class BigDecimalConverter implements Converter<Object, BigDecimal> {

    @Override
    public List<Object> getTypes() {
        return Arrays.asList(BigDecimal.class, BigDecimal.class.getName());
    }

    @Override
    public BigDecimal convert(Object value) {
        if (value == null) {
            return null;
        }
        
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else {
            String v = value.toString();
            if (v.trim().length() != 0) {
                return new BigDecimal(v);
            }
        }
        
        return null;
    }

}
