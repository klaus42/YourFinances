package ru.klaus42.mysqldemo.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.klaus42.mysqldemo.entity.Cash;
import ru.klaus42.mysqldemo.entity.Currency;
import ru.klaus42.mysqldemo.entity.User;
import ru.klaus42.mysqldemo.repository.CashRepository;
import ru.klaus42.mysqldemo.repository.CurrencyRepository;
import ru.klaus42.mysqldemo.repository.UserRepository;

import javax.validation.Valid;

@RestController
//@RequestMapping("cash")
public class CashRestController {

    @Autowired
    CashRepository cashRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CurrencyRepository currencyRepository;


}
