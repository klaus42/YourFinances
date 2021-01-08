package ru.klaus42.mysqldemo.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.klaus42.mysqldemo.entity.Cash;
import ru.klaus42.mysqldemo.entity.Currency;
import ru.klaus42.mysqldemo.entity.User;
import ru.klaus42.mysqldemo.repository.CashRepository;
import ru.klaus42.mysqldemo.repository.CurrencyRepository;
import ru.klaus42.mysqldemo.repository.UserRepository;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("cash")
public class CashRestController {

    @Autowired
    CashRepository cashRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @RequestMapping("update")
    public String updateCash(@RequestBody Map<String,String> body) {

        Map<String,String> params = body;

//        MultiValueMap<String,String> params = FormHttpMessageConverter
//
        String newCashName = params.get("name");
        Long id = Long.parseLong(params.get("id"));


        Cash cash = cashRepository.findById(id).orElse(null);
        if (cash != null) {
            cash.setName(newCashName);
            cashRepository.save(cash);
            return "Ok";
        }

        return "False";
    }

    @RequestMapping(value = "delete/{id}")
    public String deleteCash(@PathVariable("id") Long id) {


        Cash cash = cashRepository.findById(id).orElse(null);
        if (cash != null) {
            cash.setStatus(Cash.STATUS_DELETED);
            cashRepository.save(cash);
            return "Ok";
        }

        return "False";
    }

}
