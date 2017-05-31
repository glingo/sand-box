package resource.loader;

import java.io.InputStream;

public class TypedResourceLoader implements ResourceLoader {
    
    private final String type;
    private final ResourceLoader resourceLoader;

    public TypedResourceLoader(String type, ResourceLoader resourceLoader) {
        this.type = type;
        this.resourceLoader = resourceLoader;
    }

    public String getType() {
        return type;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    @Override
    public InputStream load(String path) {
        return this.resourceLoader.load(path);
    }

    @Override
    public boolean exists(String path) {
        return this.resourceLoader.exists(path);
    }
}
