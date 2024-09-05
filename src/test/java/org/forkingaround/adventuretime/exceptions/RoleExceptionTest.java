package org.forkingaround.adventuretime.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoleExceptionTest {

    @Test
    void testRoleException_WithMessage() {
        String errorMessage = "Role not found";
        RoleException exception = new RoleException(errorMessage);

        assertThat(exception).isInstanceOf(RoleException.class);
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void testRoleException_WithMessageAndCause() {
        String errorMessage = "Role creation failed";
        Throwable cause = new RuntimeException("Database error");
        RoleException exception = new RoleException(errorMessage, cause);

        assertThat(exception).isInstanceOf(RoleException.class);
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    void testRoleException_ThrownByCode() {
        assertThatThrownBy(() -> {
            throw new RoleException("This is a test exception");
        }).isInstanceOf(RoleException.class)
          .hasMessage("This is a test exception");
    }
}
