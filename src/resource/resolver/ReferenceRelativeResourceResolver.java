package resource.resolver;

import java.util.Collection;
import java.util.Optional;
import resolver.Resolver;
import resource.FileSystem;
import resource.reference.ResourceReference;

public class ReferenceRelativeResourceResolver implements Resolver<ResourceReference, ResourceReference> {
    
    private final Collection<String> types;
    private final ResourceReference relative;

    public ReferenceRelativeResourceResolver(Collection<String> types, ResourceReference relative) {
        this.types = types;
        this.relative = relative;
    }

    @Override
    public Optional<ResourceReference> resolve(ResourceReference reference) {
        if (!types.contains(reference.getType())) {
            return Optional.of(reference);
        }
        
        if (!FileSystem.isRelative(reference.getPath())) {
            return Optional.of(reference);
        }
        
        return Optional.of(ResourceReference.reference(reference.getType(), FileSystem.resolve(this.relative.getPath(), reference.getPath())));
    }

    public ResourceReference getRelative() {
        return relative;
    }

    public Collection<String> getTypes() {
        return types;
    }
}
