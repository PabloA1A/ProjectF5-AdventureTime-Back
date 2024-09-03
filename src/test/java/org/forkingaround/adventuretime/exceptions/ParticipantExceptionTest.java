package org.forkingaround.adventuretime.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantExceptionTest {

    @Test
    void testExceptionWithMessage() {
        String errorMessage = "Test message";
        ParticipantException exception = new ParticipantException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testExceptionWithMessageAndCause() {
        String errorMessage = "Test message";
        Throwable cause = new RuntimeException("Cause of the exception");
        ParticipantException exception = new ParticipantException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
