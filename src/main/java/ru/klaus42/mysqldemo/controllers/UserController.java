package ru.klaus42.mysqldemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.Errors;

import ru.klaus42.mysqldemo.entity.User;
import ru.klaus42.mysqldemo.repository.UserRepository;

import javax.validation.Valid;


@Controller
public class UserController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String signupForm(Model model, Authentication authentication) {
        if (authentication != null) {
            User user = userRepository.findByUsername(authentication.getName());
            if (user != null) {
                return "redirect:user";
            }
        }

        model.addAttribute("user", new User());
        return "user/signupform";
    }

    @PostMapping("/signup")
    public String userSubmit(@Valid User user, Errors errors, Model model) {
        if (null != errors && errors.getErrorCount() > 0) {
            return "user/signupform";
        } else {
            if (userRepository.findByEmail(user.getEmail()) != null) {
                return "user/signupform";
            }
            userRepository.save(user);

            model.addAttribute("successMsg", "Details saved successfully!!");
            return "user/userprofile";
        }
    }

    @GetMapping("/user")
    public String getUserProfile(Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());

        if (user == null) return "notfound";

        model.addAttribute("user", user);
        return "user/userprofile";
    }


}
