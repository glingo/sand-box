package resource.resolver;

import java.util.Optional;
import resource.reference.ResourceReference;

public interface RelativeResourceResolver {
     Optional<ResourceReference> resolve(ResourceReference parentReference, ResourceReference newPath);
}
