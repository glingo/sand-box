package event.crud;

public class UpdateEvent<O> extends CRUDEvent<O> {
    
    public UpdateEvent(O object) {
        super(object);
    }
    
}
