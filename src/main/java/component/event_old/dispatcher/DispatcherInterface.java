package component.event_old.dispatcher;

import component.event_old.subscriber.SubscriberInterface;

public interface DispatcherInterface<T> {

    T create();
    
    void dispatch(String name, T event) throws Exception;

    void addSubscriber(SubscriberInterface<T> sub);
    
    void removeSubscriber(SubscriberInterface<T> sub);

}
