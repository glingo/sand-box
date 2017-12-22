package component.resource.exceptions;

public class ResourceNotFoundException extends ResourceException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable e) {
        super(e);
    }
}
