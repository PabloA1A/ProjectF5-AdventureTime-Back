package org.forkingaround.adventuretime.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventExceptionTest {

    @Test
    void testEventExceptionWithMessage() {
        // Arrange
        String message = "This is an event exception message";

        // Act
        EventException exception = new EventException(message);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    void testEventExceptionWithMessageAndCause() {
        // Arrange
        String message = "This is an event exception message";
        Throwable cause = new RuntimeException("This is the cause");

        // Act
        EventException exception = new EventException(message, cause);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}
