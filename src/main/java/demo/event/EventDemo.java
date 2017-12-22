package demo.event;

import component.event.Event;
import component.event.dispatcher.EventDispatcher;
import demo.event.crud.CreateEvent;
import demo.event.crud.UpdateEvent;
import component.event.dispatcher.DispatcherInterface;
import component.event.handler.Handler;
import demo.event.model.Role;
import demo.event.model.User;
import component.event.subscriber.Subscriber;

public class EventDemo {
    
    public static void main(String[] args) throws Exception {
        
        EventDispatcher.builder()
                .register("update", 90, (Event event) -> {
                    System.out.println("builder handle Event ".concat(event.toString()));
                })
                .subscribe(new RepositorySubscriber())
                .subscribe(new Subscriber() {
                    @Override
                    public void subscribe(DispatcherInterface dispatcher) {
                        dispatcher.register("create", (Event event) -> {
                            System.out.println("builder handle Event ".concat(event.toString()));
                        });
                    }
                })
                .subscribe(new Subscriber<Event>() {
                    @Override
                    public void subscribe(DispatcherInterface<Event> dispatcher) {
                        dispatcher.of(UpdateEvent.class).register("update", (UpdateEvent event) -> {
                            System.out.println("builder handle Event ".concat(event.getObject().toString()));
                        });
                    }
                })
                .subscribe((DispatcherInterface<Event> dispatcher) -> {
                    dispatcher.of(UpdateEvent.class).register("update", (UpdateEvent event) -> {
                        System.out.println("builder handle Event ".concat(event.getObject().toString()));
                    });
                })
                .build()
                .dispatch("create", new CreateEvent("qsd"))
                .dispatch("update", new UpdateEvent("qsd"))
                .dispatch("terminate", new UpdateEvent("qsd"));
        
        EventDispatcher dispatcher = new EventDispatcher();
        Subscriber repoSubscriber = new RepositorySubscriber();
        repoSubscriber.subscribe(dispatcher);
        Subscriber userSubscriber = new UserSubscriber();
        userSubscriber.subscribe(dispatcher);
        Subscriber roleSubscriber = new RoleSubscriber();
        roleSubscriber.subscribe(dispatcher);
        
        dispatcher.register("create", (Event event) -> {
            System.out.println("handle CreateEvent");
        });
        
        dispatcher.of(CreateEvent.class).register("create", (CreateEvent event) -> {
            System.out.println("Annon handle CreateEvent".concat(event.getObject().toString()));
        });
        dispatcher.of(UpdateEvent.class).register("update", 60, (UpdateEvent event) -> {
            System.out.println("Annon handle Event".concat(event.toString()));
        });
        
        User user = new User("bernard");
        
        dispatcher.dispatch("create", new CreateEvent(user));
        dispatcher.dispatch("update", new UpdateEvent(user));
        
//        dispatcher.dispatch("create", new UserCreatedEvent(user));
//        dispatcher.dispatch("update", new UserUpdatedEvent(user));
        
        Role admin = new Role("admin");
        
        dispatcher.dispatch("create", new CreateEvent(admin));
        dispatcher.dispatch("update", new UpdateEvent(admin));
        
//        dispatcher.register("create", UserSubscriber::onCreate);
        
//        EventDispatcher dispatcher = new EventDispatcher();
//        dispatcher.register("user_created", (UserCreatedEvent event) -> {
//            event.getObject().getUsername();
//        });
////        dispatcher.addSubscriber(sub);
    }
    
    public static class RepositorySubscriber implements Subscriber {
        
        public Handler<CreateEvent> onCreate() {
            return u -> {
//                u.stopEventPropagation();
                System.out.println("handle RepositorySubscriber CreateEvent".concat(u.getObject().toString()));
            };
        }
        
        public Handler<UpdateEvent> onUpdate() {
            return u -> System.out.println("handle RepositorySubscriber UpdateEvent".concat(u.getObject().toString()));
        }

        @Override
        public void subscribe(DispatcherInterface dispatcher) {
            dispatcher.register("create", 1, onCreate());
            dispatcher.register("update", 1, onUpdate());
        }
    }
    
    public static class UserSubscriber implements Subscriber {
        
        public Handler<CreateEvent> onCreate() {
            return u -> {
                if (!(u.getObject() instanceof User)) {
                    return;
                }
                
                System.out.println("handle UserSubscriber CreateEvent".concat(u.getObject().toString()));
            };
        }
        
        public Handler<UpdateEvent> onUpdate() {
            return u -> {
                if (!(u.getObject() instanceof User)) {
                    return;
                }
                
                System.out.println("handle UserSubscriber UpdateEvent".concat(u.getObject().toString()));
            };
        }
        
        public void onUpdate2(Event event) {
            System.out.println("handle UserSubscriber onUpdate2".concat(event.toString()));
        }

        @Override
        public void subscribe(DispatcherInterface dispatcher) {
            dispatcher.register("create", 5, onCreate());
            dispatcher.register("update", 5, onUpdate());
            dispatcher.register("update", 99, this::onUpdate2);
        }
    }
        
    public static class RoleSubscriber implements Subscriber {
        
        public Handler<CreateEvent> onCreate() {
            return u -> {
                if (!(u.getObject() instanceof Role)) {
                    return;
                }
                
                System.out.println("handle RoleSubscriber CreateEvent".concat(u.getObject().toString()));
            };
        }
        
        public Handler<UpdateEvent> onUpdate() {
            return u -> {
                if (!(u.getObject() instanceof Role)) {
                    return;
                }
                
                System.out.println("handle RoleSubscriber UpdateEvent".concat(u.getObject().toString()));
            };
        }

        @Override
        public void subscribe(DispatcherInterface dispatcher) {
            dispatcher.register("create", 10, onCreate());
            dispatcher.register("update", 10, onUpdate());
        }
    }
    
}
