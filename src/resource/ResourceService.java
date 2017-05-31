package resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import resource.loader.ResourceLoader;
import resource.loader.ResourceLoaderDelegate;
import resource.metatada.ResourceMetadata;
import resource.metatada.ResourceReferenceMetadata;
import resource.reference.ResourceReference;
import resource.reference.ResourceReferenceExtractor;

public class ResourceService {
    
    private String name;
    public String getName() {
        return name;
    }
    public ResourceService(String name, ResourceLoader loader) {
        this.name = name;
        this.loader = loader;
        this.extractor = ResourceReferenceExtractor.defaultExtractor();
    }
    
    private ResourceLoader loader;
    private ResourceReferenceExtractor extractor;

    public ResourceService(ResourceLoader loader) {
        this.name = UUID.randomUUID().toString();
        this.loader = loader;
        this.extractor = ResourceReferenceExtractor.defaultExtractor();
    }
    
    public ResourceMetadata metadata(ResourceReference reference) {
        return new ResourceReferenceMetadata(this.loader, reference);
    }
    
    public ResourceMetadata metadata(String path) {
        ResourceReference reference = this.extractor.extract(path);
        return metadata(reference);
    }
    
    public InputStream load(String path) {
        ResourceMetadata metadata = metadata(path);
        return metadata.load();
    }
    
    public static ResourceServiceBuilder builder() {
        return new ResourceServiceBuilder();
    }
    
    public static ResourceServiceBuilder builder(String name) {
        return new ResourceServiceBuilder(name);
    }
    
    public static class ResourceServiceBuilder {
        
        private Collection<ResourceLoader> loaders;
        private String name;

        public ResourceServiceBuilder() {
        }

        public ResourceServiceBuilder(String name) {
            this.name = name;
        }
        
        public ResourceServiceBuilder with(ResourceLoader loader) {
            if (Objects.isNull(this.loaders)) {
                this.loaders = new ArrayList<>();
            }
            this.loaders.add(loader);
            return this;
        }
        
        public ResourceService build() {
            ResourceLoader delegate = new ResourceLoaderDelegate(loaders);
            ResourceService service = new ResourceService(name, delegate);
            return service;
        }
    }
}
