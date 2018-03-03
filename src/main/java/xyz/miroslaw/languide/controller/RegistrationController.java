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

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User userRegistrationDto() {
        return new User();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "/user/registerform";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid User user,
                                      BindingResult result){

        User existing = userService.findByName(user.getName());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            return "registration";
        }

        userService.createUser(user);
        return "redirect:/register?success";
    }

}
