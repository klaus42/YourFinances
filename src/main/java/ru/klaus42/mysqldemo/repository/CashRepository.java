package ru.klaus42.mysqldemo.repository;

import org.springframework.data.repository.CrudRepository;
import ru.klaus42.mysqldemo.entity.Cash;

public interface CashRepository extends CrudRepository<Cash,Long> {
}
