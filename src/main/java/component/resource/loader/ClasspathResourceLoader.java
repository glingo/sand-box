package component.resource.loader;

import component.utils.ClassUtils;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;

public class ClasspathResourceLoader implements ResourceLoader {
    
    public static final String ROOT_PATH = "/";
    private final ClassLoader classLoader;
    protected Class clazz;

    public ClasspathResourceLoader() {
        this(ClassUtils.getDefaultClassLoader());
    }
    
    public ClasspathResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    
    public ClasspathResourceLoader(Class clazz) {
        this.classLoader = clazz.getClassLoader();
        this.clazz = clazz;
    }

    @Override
    public Optional<Charset> getCharset(String path) {
        return Optional.empty();
    }

    @Override
    public InputStream load(String path) {
        path = getPath(path);
        if (this.clazz != null) {
            return this.clazz.getResourceAsStream(path);
        } else if (this.classLoader != null) {
            return this.classLoader.getResourceAsStream(path);
        } else {
            return ClassLoader.getSystemResourceAsStream(path);
        }
    }

    @Override
    public boolean exists(String path) {
        return load(path) != null;
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
