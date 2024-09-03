package org.forkingaround.adventuretime.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParticipantNotFoundExceptionTest {

    @Test
    void testExceptionWithMessage() {
        String errorMessage = "Participant not found";
        ParticipantNotFoundException exception = new ParticipantNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testExceptionWithMessageAndCause() {
        String errorMessage = "Participant not found";
        Throwable cause = new RuntimeException("Cause of the exception");
        ParticipantNotFoundException exception = new ParticipantNotFoundException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}


