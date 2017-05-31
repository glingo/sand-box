package resource.loader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StringResourceLoader implements ResourceLoader {

    public StringResourceLoader() {
    }
    
    @Override
    public InputStream load(String path) {
        return new ByteArrayInputStream(path.getBytes());
    }

    @Override
    public boolean exists(String path) {
        return path != null;
    }
}
