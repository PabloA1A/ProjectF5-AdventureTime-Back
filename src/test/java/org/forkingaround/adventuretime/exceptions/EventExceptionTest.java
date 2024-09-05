package org.forkingaround.adventuretime.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventExceptionTest {

    @Test
    void testEventExceptionWithMessage() {
        String message = "This is an event exception message";

        EventException exception = new EventException(message);

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    void testEventExceptionWithMessageAndCause() {
        String message = "This is an event exception message";
        Throwable cause = new RuntimeException("This is the cause");

        EventException exception = new EventException(message, cause);

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}
