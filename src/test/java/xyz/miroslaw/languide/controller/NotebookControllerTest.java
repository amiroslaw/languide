package xyz.miroslaw.languide.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.model.Translation;
import xyz.miroslaw.languide.service.NotebookServiceImp;
import xyz.miroslaw.languide.service.TranslationServiceImp;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static xyz.miroslaw.languide.UtilTest.USER;

public class NotebookControllerTest {

    @Mock
    private NotebookServiceImp serviceImp;
    @InjectMocks
    private NotebookController controller;
    private MockMvc mvc;

    private Notebook NOTEBOOK;
    private static final String NOTEBOOK_NOTEBOOKFORM = "notebook/notebookform";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();

        NOTEBOOK = Notebook.builder()
                .title("notebook")
                .description("first notebook")
                .build();
    }

    @Test
    public void showNotebooks() throws Exception {
        when(serviceImp.findUserNotebooks()).thenReturn(Collections.singleton(NOTEBOOK));

        mvc.perform(get("/user/miro/notebooks"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("notebooks"))
                .andExpect(view().name("notebook/allnotebooks"));

        verify(serviceImp, times(1)).findUserNotebooks();
    }

    @Test
    public void showNotebookForm() throws Exception {

        mvc.perform(get("/user/miro/notebook"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("notebook"))
                .andExpect(view().name(NOTEBOOK_NOTEBOOKFORM));

    }

    @Test
    public void saveOrUpdateNotebook() throws Exception {
        when(serviceImp.createOrUpdateNotebook(any(Notebook.class))).thenReturn(NOTEBOOK);

        mvc.perform(post("/user/miro/notebook")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("title", "note")
                .param("description", "first note"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/miro/notebooks"));

        verify(serviceImp, times(1)).createOrUpdateNotebook(any(Notebook.class));

    }
    @Test
    public void saveOrUpdateNotebook_shouldRedirectToForm_blankField() throws Exception {
        when(serviceImp.createOrUpdateNotebook(any(Notebook.class))).thenReturn(NOTEBOOK);

        mvc.perform(post("/user/miro/notebook")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("title", "")
                .param("description", "first note"))
                .andExpect(status().isOk())
                .andExpect(view().name(NOTEBOOK_NOTEBOOKFORM));

    }

    @Test
    public void fillNotebookForm() throws Exception {
        when(serviceImp.findById(anyLong())).thenReturn(NOTEBOOK);

        mvc.perform(get("/user/miro/notebook/2/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("notebook"))
                .andExpect(view().name(NOTEBOOK_NOTEBOOKFORM));

        verify(serviceImp, times(1)).findById(2L);
    }

    @Test
    public void deleteById() throws Exception {
        doNothing().when(serviceImp).deleteById("miro",1L);

        mvc.perform(get("/user/miro/notebook/1/delete")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(view().name("redirect:/user/miro/notebooks"))
                .andExpect(status().is3xxRedirection());

        verify(serviceImp, times(1)).deleteById("miro",1L);
    }
}