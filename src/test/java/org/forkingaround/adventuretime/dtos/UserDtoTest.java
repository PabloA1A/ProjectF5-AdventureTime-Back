package org.forkingaround.adventuretime.dtos;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

public class UserDtoTest {

    @Test
    void testUserDtoConstructorAndTestGetters() {

        String username = "testUser";
        String password = "testPass";

        UserDto userDto = new UserDto(username, password);

        assertThat(userDto.getUsername(), is(username));
        assertThat(userDto.getPassword(), is(password));
    }
}
