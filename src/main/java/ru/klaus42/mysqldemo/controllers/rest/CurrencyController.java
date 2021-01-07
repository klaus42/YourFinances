package ru.klaus42.mysqldemo.controllers.rest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.klaus42.mysqldemo.entity.Currency;
import ru.klaus42.mysqldemo.repository.CurrencyRepository;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("currencies")
public class CurrencyController {

    @Autowired
    CurrencyRepository currencyRepository;

    @GetMapping
    public List<CurrencyItem> stateItems(@RequestParam(value = "q", required = false) String query) {
        List<Currency> currs;
        if (StringUtils.isEmpty(query)) {
            currs = currencyRepository.findTop15ByOrderByDisplayNameAsc();
        } else {
            currs = currencyRepository.findByDisplayNameContaining(query);
        }

        Currency[] currencies = new Currency[currs.size()];
        currs.toArray(currencies);

        return Arrays.stream(currencies)
                .map(this::mapToStateItem)
                .collect(Collectors.toList());

    }

    private CurrencyItem mapToStateItem(Currency currency) {
        return CurrencyItem.builder()
                .id(currency.getName())
                .text(currency.getDisplayName())
                .build();
    }
}

@Getter
@Builder
class CurrencyItem {
    private String id;
    private String text;
}
