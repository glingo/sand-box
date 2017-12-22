package component.resource.loader;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import component.resource.exceptions.ResourceNotFoundException;

public class ResourceLoaderDelegate implements ResourceLoader {
    
    private Collection<ResourceLoader> loaders = new ArrayList<>();

    public ResourceLoaderDelegate() {
    }
    
    public ResourceLoaderDelegate(Collection<ResourceLoader> loaders) {
        this.loaders = loaders;
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
    
    public void with(ResourceLoader loader) {
        this.loaders.add(loader);
    }
    
    private Optional<ResourceLoader> matches(String path) {
        return this.loaders.stream()
                .filter((loader) -> loader.exists(path))
                .findFirst();
    }

    @Override
    public String toString() {
        return "ResourceLoaderDelegate{" + "loaders=" + loaders + '}';
    }
    
}
