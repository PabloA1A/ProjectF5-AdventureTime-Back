package org.forkingaround.adventuretime.exceptions;

public class EventNotFoundException extends EventException {

    public EventNotFoundException(String message) {
        super(message);
    }
    
    public EventNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
