package edu.appstate.cs.cloud.helloizer;

import org.hamcrest.core.StringStartsWith;
import org.hamcrest.core.SubstringMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloMessageControllerTest {

    @Autowired
    private MockMvc mvc;

    // This tests the use of the /hello endpoint with no parameters. We expect that
    // a) is returns with a status of Ok, b) the message value exists in the JSON that
    // comes back, and c) the value starts with "Hello, anonymous person"
    @Test
    public void testGetAnonymousMessage() throws Exception {
        mvc.perform(
                get("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value(StringStartsWith.startsWith("Hello, anonymous person")));
    }

    // This tests the use of the /hello endpoint with no parameters. We expect that
    // a) is returns with a status of Ok, b) the message value exists in the JSON that
    // comes back, and c) the value starts with "Hello, <name>", where <name> is the
    // parameter passed to the endpoint (here, "pirate")
    @Test
    public void testGetMessageWithName() throws Exception {
        mvc.perform(
                get("/hello").param("name", "pirate"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").value(StringStartsWith.startsWith("Hello, pirate")));
    }

}