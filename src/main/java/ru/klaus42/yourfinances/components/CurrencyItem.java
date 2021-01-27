package ru.klaus42.yourfinances.components;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CurrencyItem {
    private String id;
    private String text;
}
