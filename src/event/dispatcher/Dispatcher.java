package event.dispatcher;

import event.Event;
import event.handler.Handler;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Dispatcher<E extends Event> implements DispatcherInterface<E> {

    protected final Logger logger = Logger.getLogger(getClass().getName());

    private Map<String, Map<Integer, Handler<E>>> handlers;
    
    private Predicate<E> stopCondition;

    public Dispatcher() {}
    
    public Dispatcher(Map<String, Map<Integer, Handler<E>>> handlers) {
        this.handlers = handlers;
    }
    
    public Dispatcher(Predicate<E> stopCondition) {
        this.stopCondition = stopCondition;
    }

    public Dispatcher(Map<String, Map<Integer, Handler<E>>> handlers, Predicate<E> stopCondition) {
        this.handlers = handlers;
        this.stopCondition = stopCondition;
    }
    
    public Map<String, Map<Integer, Handler<E>>> getHandlers() {
        if(this.handlers == null) {
            this.handlers = new HashMap<>();
        }
        
        return this.handlers;
    }
    
    public Map<Integer, Handler<E>> getHandlers(String name) {
        return getHandlers().computeIfAbsent(name, (handlers) -> new HashMap<>());
    }
    
    public Handler<E> getHandler(String name, Integer priority) {
        return getHandlers(name).get(priority);
    }
    
    public boolean hasStopCondition(){
        return this.stopCondition != null;
    }

    public <T extends E> Dispatcher<T> of(Class<T> clazz) {
        return (Dispatcher<T>) this;
    }
    
    @Override
    public DispatcherInterface<E> register(String name, Integer priority, Handler<E> handler) {
        
        if (getHandler(name, priority) != null) {
            priority = Integer.MAX_VALUE;
        }
        
        getHandlers(name).put(priority, handler);
        return this;
    }
    
    @Override
    public DispatcherInterface<E> dispatch(String name, E event) throws Exception {
        if(!getHandlers().containsKey(name)) {
            this.logger.log(Level.FINE, "No handlers for {}", name);
            return this;
        }
        
        String msg = String.format("%s is dispatching an event : %s ", this.getClass().getName(), name);
        this.logger.finest(msg);
        
        Collection<Handler<E>> values = getHandlers(name).values();
        
        for (Handler<E> handler : values) {
            if(hasStopCondition() && this.stopCondition.test(event)) {
                break;
            }
            
            handler.handle(event);
        }
        
        return this;
    }
    
//    private Handler<E> getLowestPriority(String name) {
//        
//        getHandlers().get(name).entrySet()
//    }
    
    
//    @Override
//    public void addSubscriber(SubscriberInterface<E> sub) {
//        if (Objects.isNull(sub)) {
//            return;
//        }
//        
//        getSubscribers().add(sub);
//        this.logger.finest(String.format("Subscriber %s has been added to %s.", sub.getClass().getName(), this.getClass().getName()));
//    }
    
//    @Override
//    public void removeSubscriber(SubscriberInterface<E> subscriber) {
//        if (getSubscribers().isEmpty() || !getSubscribers().contains(subscriber)) {
//            return;
//        }
//
//        getSubscribers().remove(subscriber);
//        this.logger.finest(String.format("Subscriber %s has been removed from %s.", subscriber.getClass().getName(), this.getClass().getName()));
//    }

    
//    @Override
//    public void register(String name, Map<Integer, Handler<E>> handler) {
//        if (Objects.isNull(handler)) {
//            return;
//        }
//
//        getHandlers().put(name, handler);
//        this.logger.finest(String.format("Subscriber %s has been added to %s.", handler.getClass().getName(), this.getClass().getName()));
//    }
    
//        getHandlers().containsKey(msg)

//        for (Handler<E> handler : handlers.values()) {
//            if(hasStopCondition() || this.stopCondition.test(event)) {
//                break;
//            }
//            handler.handle(event);
//        }
        
//        List<SubscriberInterface<E>> subscribers = getSubscribers().stream()
//            .filter(Objects::nonNull)
//            .collect(Collectors.toList());
//        
//        for (SubscriberInterface<E> subscriber : subscribers) {
//            Map<String, Handler<E>> handlers = subscriber.getHandlers();
//            
//            for (Handler<E> handler : handlers.values()) {
//                if(hasStopCondition() || this.stopCondition.test(event)) {
//                    break;
//                }
//                handler.handle(event);
//            }
//        }
        
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
//    }
    
}
