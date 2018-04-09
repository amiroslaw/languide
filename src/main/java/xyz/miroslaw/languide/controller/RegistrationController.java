package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserService userService;

    @Autowired
    RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User userModel() {
        return new User();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "/user/registerform";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute @Valid User user, BindingResult result) {
        Optional<User> existing = userService.findByName(user.getName());
        if (existing.isPresent()) {
            result.rejectValue("name", null, "There is already an account registered with that name");
        }
        if (result.hasErrors()) {
            return "/user/registerform";
        }
        userService.createUser(user);
        return "/user/login";
    }

}
