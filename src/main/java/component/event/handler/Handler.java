package component.event.handler;

import component.event.Event;

@FunctionalInterface
public interface Handler<E extends Event> {
    
    void handle(E event);
}
