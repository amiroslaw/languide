package xyz.miroslaw.languide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Dictionary;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.model.Role;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final NotebookService notebookService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserRepository userRepository, NotebookService notebookService,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.notebookService = notebookService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found. Id: " + id));
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Not found: " + email));
    }

    @Override
    public List<User> findUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void deleteById(final Long id) {
        userRepository.delete(getIfExist(id));
    }

    private User getIfExist(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found: " + id));
    }

    @Override
    public User createUser(User user) {
        user.setName(user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setNotebooks(user.getNotebooks());
        user.setRoles(Collections.singletonList(new Role("ROLE_USER")));
        user.setDictionary(new Dictionary());
        return userRepository.save(user);
    }

    @Override
    public HashSet<Notebook> getUserNotebooks() {
        return getLoggedUser()
                .map(u -> (HashSet<Notebook>) notebookService.findUserNotebooks(u.getId()))
                .orElse(new HashSet<>());
    }

    @Override
    public Optional<User> getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken || auth == null) {
            return Optional.empty();
        } else {
            return findByName(auth.getName());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        final User user = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
