package resource.loader;

public abstract class TypedResourceLoader implements ResourceLoader {
    
    private final String type;

    public TypedResourceLoader(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
