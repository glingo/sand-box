package event.crud;

import event.Event;

public class CRUDEvent<O> extends Event {
    
    private O object;

    public CRUDEvent(O object) {
        this.object = object;
    }

    public O getObject() {
        return object;
    }

    public void setObject(O object) {
        this.object = object;
    }
}
