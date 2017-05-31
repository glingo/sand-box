package event.handler;

import event.Event;

@FunctionalInterface
public interface Handler<E extends Event> {
    
    void handle(E event);
}
