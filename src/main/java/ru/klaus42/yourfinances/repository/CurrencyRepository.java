package ru.klaus42.yourfinances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klaus42.yourfinances.entity.Currency;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency,String> {
    Currency findByNameOrderByDisplayNameAsc(String name);
    List<Currency> findAllByOrderByDisplayNameAsc();
    List<Currency> findTop15ByOrderByDisplayNameAsc();
    List<Currency> findByDisplayNameContaining(String name);

    Currency findByName(String name);
}
