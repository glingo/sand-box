package component.event.dispatcher;

import component.event.Event;
import component.event.handler.Handler;
import component.event.subscriber.Subscriber;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class EventDispatcher extends Dispatcher<Event> {

    public EventDispatcher() {
        super();
    }
    
    public EventDispatcher(Predicate<Event> stopCondition) {
        super(stopCondition);
    }

    @Override
    public DispatcherInterface<Event> dispatch(final String name, Event event) throws Exception {
        if (event == null) {
            event = this.create();
            this.logger.info(String.format("%s has created an event.", this.getClass().getName()));
        }

        event.setDispatcher(this);
        
        return super.dispatch(name, event);
    }

    public Event create() {
        return new Event();
    }
    
    public static EventDispatcherBuilder builder() {
        return new EventDispatcherBuilder();
    }
    
    public static class EventDispatcherBuilder {
        
        private Predicate<Event> stop;
        
        private Collection<Subscriber<Event>> subscribers = new ArrayList<>();
        
        private Map<String, Map<Integer, Handler<Event>>> handlers;
    
        public EventDispatcherBuilder willStopWhen(Predicate<Event> stop) {
            this.stop = stop;
            return this;
        }
        
        public EventDispatcherBuilder subscribe(Subscriber<Event> subscriber) {
            this.subscribers.add(subscriber);
            return this;
        }
        
        public EventDispatcherBuilder register(String name, Integer priority, Handler<Event> handler) {
            if (getHandler(name, priority) != null) {
                priority = Integer.MAX_VALUE;
            }

            getHandlers(name).put(priority, handler);
            return this;
        }
        
        public EventDispatcher build() {
            EventDispatcher dispatcher = new EventDispatcher(this.stop);
            
            this.subscribers.forEach((subscriber) -> {
                subscriber.subscribe(dispatcher);
            });
            
            return dispatcher;
        }
        
        public Map<String, Map<Integer, Handler<Event>>> getHandlers() {
            if(this.handlers == null) {
                this.handlers = new HashMap<>();
            }

            return this.handlers;
        }

        public Map<Integer, Handler<Event>> getHandlers(String name) {
            return getHandlers().computeIfAbsent(name, (handlers) -> new HashMap<>());
        }

        public Handler getHandler(String name, Integer priority) {
            return getHandlers(name).get(priority);
        }
    }

}
