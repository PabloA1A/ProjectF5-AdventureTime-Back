package org.forkingaround.adventuretime.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EventNotFoundExceptionTest {

    @Test
    void testEventNotFoundExceptionWithMessage() {
        String message = "Event not found";
        EventNotFoundException exception = new EventNotFoundException(message);

        // Verificar que el mensaje de la excepción es correcto
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testEventNotFoundExceptionWithMessageAndCause() {
        String message = "Event not found";
        Throwable cause = new RuntimeException("Root cause");
        EventNotFoundException exception = new EventNotFoundException(message, cause);

        // Verificar que el mensaje de la excepción es correcto
        assertEquals(message, exception.getMessage());

        // Verificar que la causa de la excepción es correcta
        assertEquals(cause, exception.getCause());
    }
}
