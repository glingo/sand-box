package component.event;

import component.event.dispatcher.Dispatcher;
import java.util.UUID;

public class Event {

    private long id;
    private boolean propagationStopped = false;
    private Dispatcher dispatcher;

    public Event() {
        super();
        this.id = UUID.randomUUID().getMostSignificantBits();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public boolean isPropagationStopped() {
        return propagationStopped;
    }

    public void stopEventPropagation() {
        this.propagationStopped = true;
    }

}
