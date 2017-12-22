package demo.event.crud;

public class CreateEvent<O> extends CRUDEvent<O> {
    
    public CreateEvent(O object) {
        super(object);
    }
    
}
