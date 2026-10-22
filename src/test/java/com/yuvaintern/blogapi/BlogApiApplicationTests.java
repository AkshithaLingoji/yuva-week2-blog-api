
package com.yuvaintern.blogapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listPostsReturnsSuccess() throws Exception {
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk());
    }

    @Test
    void registerUserReturnsCreated() throws Exception {
        String email = "test-" + System.nanoTime() + "@example.com";

        String body = """
                {
                  "name": "Test User",
                  "email": "%s",
                  "password": "TestPass123!"
                }
                """.formatted(email);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    void registeringWithMissingRequiredFieldsIsRejected()
            throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void gettingMissingPostReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/posts/999999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void duplicateEmailRegistrationReturnsConflict() throws Exception {
        String email = "duplicate-" + System.nanoTime() + "@example.com";

        String body = """
                {
                  "name": "Test User",
                  "email": "%s",
                  "password": "TestPass123!"
                }
                """.formatted(email);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }
    
@Test
void duplicateEmailWithDifferentCapitalizationReturnsConflict()
        throws Exception {
    String email = "CaseTest-" + System.nanoTime() + "@example.com";

    String firstBody = """
            {
              "name": "Test User",
              "email": "%s",
              "password": "TestPass123!"
            }
            """.formatted(email);

    String secondBody = """
            {
              "name": "Test User",
              "email": "%s",
              "password": "TestPass123!"
            }
            """.formatted(email.toLowerCase(java.util.Locale.ROOT));

    mockMvc.perform(post("/api/users/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(firstBody))
            .andExpect(status().isCreated());

    mockMvc.perform(post("/api/users/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(secondBody))
            .andExpect(status().isConflict());
}

}
