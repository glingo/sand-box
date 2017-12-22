package component.event_old;

import component.event_old.subscriber.SubscriberInterface;
import java.util.Map;
import java.util.logging.Logger;

public abstract class EventSubscriber<T extends Event> implements SubscriberInterface<T> {

    protected final Logger logger = Logger.getLogger(getClass().getName());
    
    @Override
    public abstract Map<String, EventConsumer<T>> getSubscribedEvents();

}
