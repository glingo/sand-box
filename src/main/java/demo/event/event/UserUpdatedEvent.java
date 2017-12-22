package demo.event.event;

import demo.event.crud.CreateEvent;
import demo.event.model.User;

public class UserUpdatedEvent extends CreateEvent<User>{
    
    public UserUpdatedEvent(User object) {
        super(object);
    }
    
}
