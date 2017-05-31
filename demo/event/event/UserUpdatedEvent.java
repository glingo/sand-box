package event.event;

import event.crud.CreateEvent;
import event.model.User;

public class UserUpdatedEvent extends CreateEvent<User>{
    
    public UserUpdatedEvent(User object) {
        super(object);
    }
    
}
