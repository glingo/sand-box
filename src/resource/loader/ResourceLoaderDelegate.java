package resource.loader;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Optional;
import resource.exceptions.ResourceNotFoundException;

public class ResourceLoaderDelegate implements ResourceLoader {
    
    private final Collection<ResourceLoader> resourceLoaders;

    public ResourceLoaderDelegate(Collection<ResourceLoader> resourceLoaders) {
        this.resourceLoaders = resourceLoaders;
    }

    @Override
    public Optional<Charset> getCharset(String path) {
        Optional<ResourceLoader> loader = matches(path);
        if (loader.isPresent()) {
            return loader.get().getCharset(path);
        }
        return Optional.empty();
    }

    @Override
    public InputStream load(String path) {
        Optional<ResourceLoader> loader = matches(path);
        if (loader.isPresent()) {
            return loader.get().load(path);
        }
        
        throw new ResourceNotFoundException(String.format("Resource '%s' not found", path));
    }

    @Override
    public boolean exists(String path) {
        return matches(path).isPresent();
    }

    @Override
    public Optional<URL> toUrl(String path) {
        Optional<ResourceLoader> loader = matches(path);
        if (loader.isPresent()) {
            return loader.get().toUrl(path);
        }
        
        return Optional.empty();
    }
    
    private Optional<ResourceLoader> matches(String path) {
        return this.resourceLoaders.stream()
                .filter((loader) -> loader.exists(path)).findFirst();
    }
}
