package org.forkingaround.adventuretime.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ContextConfiguration
public class AuthTest {

    @Autowired
    private WebApplicationContext context;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser // @WithMockUser(username="minnie") podemos personalizar el username y más...
    void testUsingAnnotationMockUser() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/login"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        System.out.println(response.getContentAsString());

        assertThat(response.getContentAsString(), containsString("roles"));
        assertThat(response.getContentAsString(), containsString("Logged"));

        assertThat(response.getContentAsString(),
                is("{\"roles\":\"ROLE_USER\",\"message\":\"Logged\",\"username\":\"user\"}"));
    }

    @Test
    void testAllCanAccessPathRegister() throws Exception {
        mockMvc.perform(post("/api/v1/register"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
    }


}
