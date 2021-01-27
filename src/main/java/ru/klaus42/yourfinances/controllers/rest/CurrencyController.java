package ru.klaus42.yourfinances.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.klaus42.yourfinances.components.CurrencyItem;
import ru.klaus42.yourfinances.entity.Currency;
import ru.klaus42.yourfinances.repository.CurrencyRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("currencies")
public class CurrencyController {

    @Autowired
    CurrencyRepository currencyRepository;

    @GetMapping
    public List<CurrencyItem> stateItems(@RequestParam(value = "q", required = false) String query) {
        List<Currency> currs;
        if (ObjectUtils.isEmpty(query)) {
            currs = currencyRepository.findTop15ByOrderByDisplayNameAsc();
        } else {
            currs = currencyRepository.findByDisplayNameContaining(query);
        }

        Currency[] currencies = new Currency[currs.size()];
        currs.toArray(currencies);

        return Arrays.stream(currencies)
                .map(this::mapToCurrencyItem)
                .collect(Collectors.toList());

    }

    private CurrencyItem mapToCurrencyItem(Currency currency) {
        return CurrencyItem.builder()
                .id(currency.getName())
                .text(currency.getDisplayName())
                .build();
    }
}

