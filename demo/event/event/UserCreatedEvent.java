package event.event;

import event.crud.CreateEvent;
import event.model.User;

public class UserCreatedEvent extends CreateEvent<User>{
    
    public UserCreatedEvent(User object) {
        super(object);
    }
    
}
