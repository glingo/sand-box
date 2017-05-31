package resolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class ResolverDelegate<I, O> implements Resolver<I, O> {

    protected Map<Predicate<I>, Resolver<I, O>> resolvers;

    public ResolverDelegate() {
        this.resolvers = new HashMap<>();
    }
    
    public ResolverDelegate(Map<Predicate<I>, Resolver<I, O>> resolvers) {
        this.resolvers = resolvers;
    }

    @Override
    public Optional<O> resolve(I object) {
        Optional<Resolver<I, O>> resolver = getResolvers().entrySet().stream()
                .filter((a) -> a.getKey().test(object))
                .map((e) -> e.getValue())
                .findFirst();

        if (resolver.isPresent()) {
            return resolver.get().resolve(object);
        }
        return Optional.empty();
    }

    public Map<Predicate<I>, Resolver<I, O>> getResolvers() {
        if(this.resolvers == null) {
            this.resolvers = new HashMap<>();
        }
        return this.resolvers;
    }

    public void setResolvers(Map<Predicate<I>, Resolver<I, O>> resolvers) {
        this.resolvers = resolvers;
    }
    
    public void addResolver(Predicate<I> predicate, Resolver<I, O> resolver) {
        getResolvers().put(predicate, resolver);
    }
}
