package xyz.miroslaw.languide.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.miroslaw.languide.UtilTest;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.repository.NotebookRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class NotebookServiceImpTest {
    @Mock
    private NotebookRepository repository;
    @Mock
    private UserService userService;
    @InjectMocks
    private NotebookServiceImp service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findById_shouldFind() {
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(UtilTest.NOTEBOOK));

        Notebook notebook = service.findById(anyLong());

        assertThat(notebook).isNotNull().isEqualTo(UtilTest.NOTEBOOK);
        verify(repository, times(1)).findById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void findById_shouldThrowNotFound() {
        when(repository.findById(anyLong())).thenReturn(null);

        service.findById(1L);

        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void deleteById_shouldDelete() {
        when(repository.findByUserNameAndId(anyString(), anyLong())).thenReturn(Optional.ofNullable(UtilTest.NOTEBOOK));
        doNothing().when(repository).delete(any(Notebook.class));

        service.deleteById(anyString(), anyLong());

        verify(repository,times(1)).delete(any(Notebook.class));
    }
    @Test(expected = NotFoundException.class)
    public void deleteById_shouldThrowNotFound() {
        when(repository.findByUserNameAndId(anyString(), anyLong())).thenReturn(Optional.empty());

        service.deleteById(anyString(), anyLong());

        verify(repository,times(1)).delete(any(Notebook.class));
    }

    @Test
    public void findUserNotebooks_shouldFindOne() {
        when(userService.getLoggedUser()).thenReturn(Optional.ofNullable(UtilTest.USER));
        when(repository.findAllByUserId(1L)).thenReturn(Collections.singleton(UtilTest.NOTEBOOK));

        Set<Notebook> notebooks = service.findUserNotebooks();

        assertThat(notebooks).contains(UtilTest.NOTEBOOK);
        verify(repository,times(1)).findAllByUserId(1L);
    }

    @Test
    public void findUserNotebooks_shouldReturnEmptySet() {
        when(userService.getLoggedUser()).thenReturn(Optional.empty());

        Set<Notebook> notebooks = service.findUserNotebooks();

        assertThat(notebooks).isEmpty();
    }
}