package event_old.subscriber;

import event_old.EventConsumer;
import java.util.Map;

public interface SubscriberInterface<T> {
    
    Map<String, EventConsumer<T>> getSubscribedEvents();
    
}
