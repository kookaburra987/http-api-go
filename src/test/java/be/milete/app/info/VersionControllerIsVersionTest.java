package be.milete.app.info;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class VersionControllerIsVersionTest {

    @Autowired
    private WebApplicationContext context;

    private static MockMvc mockMvc;

    @BeforeEach
    public void createMockMvc(){
        mockMvc = webAppContextSetup(context).build();
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

}
