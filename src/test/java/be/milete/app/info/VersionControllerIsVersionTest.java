package be.milete.app.info;

import be.milete.app.exception.GlobalExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VersionControllerIsVersionTest {

    private static MockMvc mockMvc;

    @BeforeEach
    public void createMockMvc(){
        mockMvc = MockMvcBuilders.standaloneSetup(VersionController.class)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void givenCorrectVersionReturnsTrue() throws Exception {
        mockMvc.perform(post("/info/version/equals").content("0.0.1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void givenIncorrectVersionReturnsFalse() throws Exception {
        mockMvc.perform(post("/info/version/equals").content("0.0.2"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void givenBlankVersionShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/info/version/equals").content("\t\n "))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"version must not be blank\"}"));
    }

    @Test
    void givenVersionOf17CharactersShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/info/version/equals").content(StringUtils.repeat('1', 17)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\":\"version must be max 16 characters\"}"));
    }
}
