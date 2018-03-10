package xyz.miroslaw.languide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User userModel() {
        return new User();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "/user/registerform";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute @Valid User user,
                                      BindingResult result) {
        Optional<User> existing = userService.findByName(user.getName());
        if (existing.isPresent()) {
            result.rejectValue("name", null, "There is already an account registered with that name");
        }
        if (result.hasErrors()) {
            return "/user/registerform";
        }
        userService.createUser(user);
        return "/user/login";
//            return "redirect:/register?success";
    }

}
