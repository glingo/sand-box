package resource.resolver.path;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import resource.FileSystem;
import resource.reference.ResourceReference;
import resource.resolver.ReferenceRelativeResourceResolver;

public class RelativeFilePathResolver extends ReferenceRelativeResourceResolver {

    public RelativeFilePathResolver(ResourceReference parent) {
        super(Arrays.asList(ResourceReference.FILE), parent);
    }
    
    @Override
    public Optional<ResourceReference> resolve(ResourceReference child) {
        if (FileSystem.isRelative(child.getPath())) {
            File parent = new File(getRelative().getPath());
            File relative = new File(parent.getParentFile(), child.getPath());
            return Optional.ofNullable(ResourceReference.file(relative));
        }
        return Optional.of(child);
    }
    
}
