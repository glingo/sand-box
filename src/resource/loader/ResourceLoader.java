package resource.loader;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;

public interface ResourceLoader {
    InputStream load (String path);
    boolean exists (String path);
    
    default Optional<Charset> getCharset(String path) {
        return Optional.empty();
    }
    
    default Optional<URL> toUrl (String path) {
        return Optional.empty();
    }
}
