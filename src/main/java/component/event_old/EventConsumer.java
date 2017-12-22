package component.event_old;

@FunctionalInterface
public interface EventConsumer<T> {
    
    void consume(T event) throws Exception;
}
