package event_old.subscriber;

import event_old.EventConsumer;
import java.util.Map;

public abstract class Subscriber<T> implements SubscriberInterface<T> {

    @Override
    public abstract Map<String, EventConsumer<T>> getSubscribedEvents();
    
}
