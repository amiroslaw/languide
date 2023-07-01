package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.service.UserService;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

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
        return "user/registerform";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute @Valid User user, BindingResult result) {
        return userService.findByName(user.getName())
                .map(e -> addError(result))
                .orElseGet(() -> {
                    userService.createUser(user);
                    return "user/login";
                });
    }

    private String addError(BindingResult result) {
        result.rejectValue("name", null, "There is already an account registered with that name");
        return "user/registerform";
    }

}
