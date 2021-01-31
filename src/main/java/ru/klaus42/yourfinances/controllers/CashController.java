package ru.klaus42.yourfinances.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.klaus42.yourfinances.entity.Cash;
import ru.klaus42.yourfinances.entity.Currency;
import ru.klaus42.yourfinances.entity.User;
import ru.klaus42.yourfinances.repository.CashRepository;
import ru.klaus42.yourfinances.repository.CurrencyRepository;
import ru.klaus42.yourfinances.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CashController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @Autowired
    private CashRepository cashRepository;

    @Autowired
    private CurrencyRepository currencyRepository;


    @GetMapping("/user/cash")
    public String getUserProfile(Model model, Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName());
        List<Cash> cashList = user.getCash();

        Cash newCash = new Cash();

        List<Currency> currencyList = currencyRepository.findTop15ByOrderByDisplayNameAsc();
//        List<Currency> currencyList = new ArrayList<>();

        if (user == null) return "notfound";

        model.addAttribute("user", user);
        model.addAttribute("cashList", cashList);
        model.addAttribute("cash", newCash);
        model.addAttribute("currency", currencyList);
        return "user/userprofile";
    }

    @PostMapping("/cash")
    public String cashSubmit(@Valid Cash cash, Errors errors, Model model, Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName());

        Currency currency = currencyRepository.findByNameOrderByDisplayNameAsc(cash.getCurrency().getName());

        if (currency == null || (null != errors && errors.getErrorCount() > 0)) {
            model.addAttribute(user);
            model.addAttribute("cashList", user.getCash());
            return "user/userprofile";
        } else {
            cash.setUser(user);
            cash.setCurrency(currency);
            cashRepository.save(cash);
            return "redirect:user/cash";
        }
    }

    @RequestMapping(path = "/cash/edit/{id}", produces = "application/json")
    public @ResponseBody Cash editCash(@PathVariable Long id, Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        List<Cash> cashList = user.getCash();

        Cash newCash = cashRepository.findById(id).orElse(null);

//        System.out.println(newCash.getName());

        if (user == null) return null;


        return newCash;
    }


}
