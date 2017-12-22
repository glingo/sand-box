package component.resource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import component.resource.exceptions.ResourceException;
import component.resource.loader.ResourceLoader;
import component.resource.loader.ResourceLoaderDelegate;
import component.resource.metatada.ResourceMetadata;
import component.resource.metatada.ResourceReferenceMetadata;
import component.resource.reference.ResourceReference;
import component.resource.reference.ResourceReferenceExtractor;
import component.utils.Assert;

public class ResourceService {
    
    private final Map<String, ResourceLoader> loaders;
    
    private final ResourceReferenceExtractor extractor;

    public ResourceService(Map<String, ResourceLoader> loaders) {
        this(loaders, ResourceReferenceExtractor.defaultExtractor());
    }
    
    public ResourceService(Map<String, ResourceLoader> loaders, ResourceReferenceExtractor extractor) {
        this.loaders = loaders;
        this.extractor = extractor;
    }
    
    public ResourceMetadata metadata(ResourceReference reference) {
        Assert.notNull(reference);
        Assert.hasLength(reference.getPath());
        
        Optional<ResourceLoader> optional;
        
        switch(reference.getType()) {
            case ResourceReference.ANY_TYPE:
                optional = this.loaders.values().stream()
                    .filter((loader) -> loader.exists(reference.getPath()))
                    .findFirst();
                break;
                
            default:
                optional = Optional.ofNullable(this.loaders.get(reference.getType()));
                break;
        }
        
        if (!optional.isPresent()) {
            throw new ResourceException(String.format("Cannot load resource %s. Resource loader for type '%s' not configured", reference, reference.getType()));
        }
        
        return new ResourceReferenceMetadata(optional.get(), reference);
    }
    
    public ResourceMetadata metadata(String path) {
        ResourceReference reference = this.extractor.extract(path);
        return metadata(reference);
    }
    
    public InputStream load(String path) {
        return metadata(path).load();
    }
    
    public void addLoader(String key, ResourceLoader loader) {
        if (!this.loaders.containsKey(key)) {
            this.loaders.put(key, new ResourceLoaderDelegate());
        }
        ((ResourceLoaderDelegate) this.loaders.get(key)).with(loader);
    }
    
    public static ResourceServiceBuilder builder() {
        return new ResourceServiceBuilder();
    }
    
    public static class ResourceServiceBuilder {
        
        private Map<String, ResourceLoader> loaders;

        public ResourceServiceBuilder() {
        }

        public ResourceServiceBuilder with(String type, ResourceLoader loader) {
            if (Objects.isNull(this.loaders)) {
                this.loaders = new HashMap<>();
            }
            
            if (!this.loaders.containsKey(type)) {
                this.loaders.put(type, new ResourceLoaderDelegate());
            }
            
            ((ResourceLoaderDelegate) this.loaders.get(type)).with(loader);
            return this;
        }
        
        public ResourceService build() {
            ResourceService service = new ResourceService(this.loaders);
            return service;
        }
    }

    @Override
    public String toString() {
        return "ResourceService{" + "loaders=" + loaders + ", extractor=" + extractor + '}';
    }
}
