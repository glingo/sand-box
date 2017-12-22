package component.event.subscriber;

import component.event.Event;
import component.event.dispatcher.DispatcherInterface;

@FunctionalInterface
public interface Subscriber<E extends Event> {

    void subscribe(DispatcherInterface<E> dispatcher);

}
