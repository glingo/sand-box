package event.subscriber;

import event.Event;
import event.dispatcher.DispatcherInterface;

@FunctionalInterface
public interface Subscriber<E extends Event> {

    void subscribe(DispatcherInterface<E> dispatcher);

}
