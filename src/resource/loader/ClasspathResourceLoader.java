package resource.loader;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;
import resource.exceptions.ResourceNotFoundException;
import utils.ClassUtils;

public class ClasspathResourceLoader implements ResourceLoader {
    
    public static final String ROOT_PATH = "/";
    private final ClassLoader classLoader;

    public ClasspathResourceLoader() {
        this(ClassUtils.getDefaultClassLoader());
    }
    
    public ClasspathResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Optional<Charset> getCharset(String path) {
        return Optional.empty();
    }

    @Override
    public InputStream load(String path) {
        InputStream result = classLoader.getResourceAsStream(getPath(path));
        if (result == null) {
            throw new ResourceNotFoundException(String.format("Resource '%s' not found", path));
        }
        return result;
    }

    @Override
    public boolean exists(String path) {
//        URL result = classLoader.getResource(getPath(path));
        InputStream result = classLoader.getResourceAsStream(getPath(path));
        return result != null;
    }

    @Override
    public Optional<URL> toUrl(String path) {
        return Optional.ofNullable(classLoader.getResource(getPath(path)));
    }

    private String getPath(String path) {
        if (path.startsWith(ROOT_PATH)) {
            return path.substring(1);
        }
        return path;
    }
    
}
