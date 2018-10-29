package xyz.miroslaw.languide.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.miroslaw.languide.UtilTest;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.service.UserServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RegistrationControllerTest {

    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private RegistrationController controller;
    private MockMvc mvc;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    public void registerUserAccount_loginExist_shouldGoToForm() throws Exception {
        when(userService.findByName(anyString())).thenReturn(Optional.ofNullable(UtilTest.USER));

        mvc.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "miro")
                .param("email", "miro@gmail.com")
                .param("password", "qwerty"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/registerform"));

        verify(userService, times(1)).findByName(anyString());
    }
    @Test
    public void registerUserAccount_invalidPass_shouldGoToForm() throws Exception {
        when(userService.findByName(anyString())).thenReturn(Optional.empty());

        mvc.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "miro")
                .param("email", "miro@gmail.com")
                .param("password", "fj"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/registerform"));

        verify(userService, times(1)).findByName(anyString());
    }

    @Test
    public void registerUserAccount_formValid_shouldGoToLogin() throws Exception {
        when(userService.findByName(anyString())).thenReturn(Optional.empty());
        when(userService.createUser(any(User.class))).thenReturn(UtilTest.USER);

        mvc.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "miro")
                .param("email", "miro@gmail.com")
                .param("password", "qwerty"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"));

        verify(userService, times(1)).findByName(anyString());
        verify(userService, times(1)).createUser(any(User.class));
    }
}