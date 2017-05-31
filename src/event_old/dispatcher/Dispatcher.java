package event_old.dispatcher;

import event_old.Event;
import event_old.EventConsumer;
import java.util.List;
import java.util.ArrayList;

import event_old.subscriber.SubscriberInterface;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class Dispatcher<T extends Event> implements DispatcherInterface<T> {

    protected final Logger logger = Logger.getLogger(getClass().getName());

    private List<SubscriberInterface<T>> subscribers;
    
    private Predicate<T> stopCondition;

    public Dispatcher() {}
    
    public Dispatcher(List<SubscriberInterface<T>> subscribers) {
        this.subscribers = subscribers;
    }
    
    public Dispatcher(Predicate<T> stopCondition) {
        this.stopCondition = stopCondition;
    }
    
    public Dispatcher(List<SubscriberInterface<T>> subscribers, Predicate<T> stopCondition) {
        this.subscribers = subscribers;
        this.stopCondition = stopCondition;
    }

    public List<SubscriberInterface<T>> getSubscribers() {
        if(this.subscribers == null) {
            this.subscribers = new ArrayList<>();
        }
        
        return this.subscribers;
    }
    
    public boolean hasStopCondition(){
        return this.stopCondition != null;
    }
    
    @Override
    public void addSubscriber(SubscriberInterface<T> sub) {
        if (Objects.isNull(sub)) {
            return;
        }
        
        getSubscribers().add(sub);
        this.logger.finest(String.format("Subscriber %s has been added to %s.", sub.getClass().getName(), this.getClass().getName()));
    }
    
    @Override
    public void removeSubscriber(SubscriberInterface<T> subscriber) {
        if (getSubscribers().isEmpty() || !getSubscribers().contains(subscriber)) {
            return;
        }

        getSubscribers().remove(subscriber);
        this.logger.finest(String.format("Subscriber %s has been removed from %s.", subscriber.getClass().getName(), this.getClass().getName()));
    }

    @Override
    public void dispatch(String name, T event) throws Exception {
        String msg = String.format("%s is dispatching an event : %s ", this.getClass().getName(), name);
        this.logger.finest(msg);
        
        List<SubscriberInterface<T>> subscribers = getSubscribers().stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        
        for (SubscriberInterface<T> subscriber : subscribers) {
            
            Map<String, EventConsumer<T>> subscribedEvents = subscriber.getSubscribedEvents();
            
            for (EventConsumer<T> consumer : subscribedEvents.values()) {
                if(!hasStopCondition() || !this.stopCondition.test(event)) {
                    consumer.consume(event);
                }
            }
        }
        
//        getSubscribers().stream()
//            .filter(subscriber -> subscriber != null)
//            .forEach((SubscriberInterface<T> subscriber) -> {
//                subscriber.getSubscribedEvents().entrySet().stream()
//                    .filter(entry -> name.equals(entry.getKey()))
//                    .map(entry -> entry.getValue())
//                    .forEach(listener -> {
//                        if(!hasStopCondition() || !this.stopCondition.test(event)) {
//                            listener.consume(event);
//                        }
//                    });
//            });
    }
    
}
