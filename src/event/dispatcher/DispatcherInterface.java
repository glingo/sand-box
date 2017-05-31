package event.dispatcher;

import event.Event;
import event.handler.Handler;
import java.util.Map;

public interface DispatcherInterface<E extends Event> {
    
    <T extends E> Dispatcher<T> of(Class<T> clazz);
    DispatcherInterface<E> dispatch(String name, E event) throws Exception;
    DispatcherInterface<E> register(String name, Integer priority, Handler<E> handler);
    
    default DispatcherInterface<E> register(String name, Handler<E> handler) {
        register(name, 1, handler);
        return this;
    }
    
    default DispatcherInterface<E> register(String name, Map<Integer, Handler<E>> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return this;
        }
        
        handlers.forEach((priority, handler) -> register(name, priority, handler));
        return this;
    }
}
