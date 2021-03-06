package component.resource.metatada;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;
import component.resource.loader.ResourceLoader;
import component.resource.reference.ResourceReference;

public class ResourceReferenceMetadata implements ResourceMetadata {
    
    private final ResourceLoader resourceLoader;
    private final ResourceReference resourceReference;

    public ResourceReferenceMetadata(ResourceLoader resourceLoader, ResourceReference resourceReference) {
        this.resourceLoader = resourceLoader;
        this.resourceReference = resourceReference;
    }

    @Override
    public boolean exists() {
        return resourceLoader.exists(resourceReference.getPath());
    }

    @Override
    public InputStream load() {
        return resourceLoader.load(resourceReference.getPath());
    }

    @Override
    public Optional<Charset> getCharset() {
        return resourceLoader.getCharset(resourceReference.getPath());
    }

    @Override
    public Optional<URL> toUrl () {
        return resourceLoader.toUrl(resourceReference.getPath());
    }

    public ResourceReference getResourceReference() {
        return resourceReference;
    }
}
