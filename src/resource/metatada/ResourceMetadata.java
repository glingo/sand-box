package resource.metatada;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;

public interface ResourceMetadata {
    boolean exists ();
    InputStream load();
    Optional<Charset> getCharset ();
    Optional<URL> toUrl ();
}
