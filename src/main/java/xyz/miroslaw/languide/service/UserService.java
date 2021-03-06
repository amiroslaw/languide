package xyz.miroslaw.languide.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User findById(Long id);
    User findByEmail(String email);
    Optional<User> findByName(String email);
    User createUser(User user);
    List<User> findUsers();
    void deleteById(Long id);
    HashSet<Notebook> getUserNotebooks();
    Optional<User> getLoggedUser();
}