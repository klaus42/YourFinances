package ru.klaus42.yourfinances.repository;

import org.springframework.data.repository.CrudRepository;
import ru.klaus42.yourfinances.entity.Cash;

import java.util.List;

public interface CashRepository extends CrudRepository<Cash, Long> {
    List<Cash> findAllByStatus(Short status);
    List<Cash> findAllByStatusAndUserId(Short status, Long userId);
}
