package ru.klaus42.yourfinances.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.klaus42.yourfinances.entity.Cash;
import ru.klaus42.yourfinances.repository.CashRepository;
import ru.klaus42.yourfinances.repository.CurrencyRepository;
import ru.klaus42.yourfinances.repository.UserRepository;

import java.util.Map;

@RestController
@RequestMapping("/cash")
public class CashRestController {

    @Autowired
    private CashRepository cashRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @RequestMapping("/update")
    @ResponseBody
    public Boolean updateCash(@RequestBody Map<String, String> body) {

        Map<String, String> params = body;

        String newCashName = params.get("name");
        Long id = Long.parseLong(params.get("id"));

        System.out.println(newCashName);

        Cash cash = cashRepository.findById(id).orElse(null);
        if (cash != null) {
            cash.setName(newCashName);
            cashRepository.save(cash);
            return true;
        }

        return false;
    }

    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public Boolean deleteCash(@PathVariable("id") Long id) {

        Cash cash = cashRepository.findById(id).orElse(null);
        if (cash != null) {
            cash.setStatus(Cash.STATUS_DELETED);
            cashRepository.save(cash);
            return true;
        }

        return false;
    }

}
