package xyz.miroslaw.languide.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import xyz.miroslaw.languide.model.User;

public interface UserService extends UserDetailsService {

    User findById(Long id);
    User findByEmail(String email);
    User findByName(String email);
    User createUser(User user);
    void deleteById(Long id);

    void updateUser(Long id);
}