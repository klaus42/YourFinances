package ru.klaus42.yourfinances.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.klaus42.yourfinances.entity.User;
import ru.klaus42.yourfinances.repository.UserRepository;

import java.security.Principal;


@Controller
public class DefaultController {

    @GetMapping("/")
    public String index(Principal principal, Model model) {
        model.addAttribute("module","home");
        return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
    }


    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

//    @PostMapping(path = "/add") // Map ONLY POST Requests
//    public @ResponseBody
//    String addNewUser(@RequestParam String name
//            , @RequestParam String email) {
//        // @ResponseBody means the returned String is the response, not a view name
//        // @RequestParam means it is a parameter from the GET or POST request
//
//        User n = new User();
//        n.setName(name);
//        n.setEmail(email);
//        userRepository.save(n);
//        return "Saved";
//    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }


}
