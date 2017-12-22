package demo.event.event;

import demo.event.crud.CreateEvent;
import demo.event.model.User;

public class UserCreatedEvent extends CreateEvent<User>{
    
    public UserCreatedEvent(User object) {
        super(object);
    }
    
}
