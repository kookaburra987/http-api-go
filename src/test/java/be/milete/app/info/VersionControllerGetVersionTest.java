package be.milete.app.info;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class VersionControllerGetVersionTest {

    @Autowired
    private WebApplicationContext context;

    private static MockMvc mockMvc;

    @BeforeEach
    public void createMockMvc(){
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    void getVersionReturnsVersion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/info/version"))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0.1"));
    }
}
