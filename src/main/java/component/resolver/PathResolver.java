package component.resolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class PathResolver<O> implements Resolver<String, O> {

    private String prefix;
    private String sufix;
    
    public PathResolver() {
    }
    
    public PathResolver(String prefix, String sufix) {
        this.prefix = prefix;
        this.sufix = sufix;
    }

    @Override
    public Optional<O> resolve(String object) {
        return doResolve(resolvePath(object));
    }
    
    protected abstract Optional<O> doResolve(String object);
    
    protected String resolvePath(String path) {
        return Arrays.asList(this.prefix, path, this.sufix).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining());
    }
    
    public String getSufix() {
        return sufix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSufix(String sufix) {
        this.sufix = sufix;
    }
    
}
