package component.resolver;

import java.util.Optional;

@FunctionalInterface
public interface Resolver<I, O> {
    
    Optional<O> resolve(I object);
}
