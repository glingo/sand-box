package component.event_old;

import component.event_old.dispatcher.Dispatcher;

public class EventDispatcher extends Dispatcher<Event> {

    public EventDispatcher() {
        super(event -> event.isPropagationStopped());
    }

    @Override
    public void dispatch(final String name, Event event) throws Exception {
        if (event == null) {
            event = this.create();
            this.logger.info(String.format("%s has created an event.", this.getClass().getName()));
        }

        event.setDispatcher(this);
        
        super.dispatch(name, event);
    }

    @Override
    public Event create() {
        return new Event();
    }
}
