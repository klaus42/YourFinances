package ru.klaus42.yourfinances.repository;

import org.springframework.data.repository.CrudRepository;
import ru.klaus42.yourfinances.entity.Cash;

public interface CashRepository extends CrudRepository<Cash,Long> {
}
