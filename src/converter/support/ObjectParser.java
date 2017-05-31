package converter.support;

import converter.Parser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Converts a byte array to an object via deserialization, or returns the value
 * as-is
 *
 */
public class ObjectParser implements Parser<Object> {

    @Override
    public Object[] getTypeKeys() {
        return new Object[]{
            Object.class,
            Object.class.getName()
        };
    }

    @Override
    public Object parse(Object value) {
        if (value == null) {
            return null;
        }
        if (value.getClass().isArray()) {
            // This is a byte array; presume we can convert it to an object
            if (value.getClass().getComponentType() == Byte.TYPE) {
                ByteArrayInputStream bis
                        = new ByteArrayInputStream((byte[]) value);
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(bis);
                    value = ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new IllegalArgumentException(
                            "Could not deserialize object", e);
                } finally {
                    try {
                        if (ois != null) {
                            ois.close();
                        }
                    } catch (IOException e) {
                        // Ignore
                    }
                    try {
                        if (bis != null) {
                            bis.close();
                        }
                    } catch (IOException e) {
                        // Ignore
                    }
                }
            }
        }

        return value;
    }
}
