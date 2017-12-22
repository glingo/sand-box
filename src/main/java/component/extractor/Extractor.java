package component.extractor;

@FunctionalInterface
public interface Extractor<T, R> {
    
    R extract(T spec);
}
