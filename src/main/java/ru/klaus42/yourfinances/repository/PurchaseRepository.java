package ru.klaus42.yourfinances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klaus42.yourfinances.entity.Purchase;
import ru.klaus42.yourfinances.entity.User;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    List<Purchase> findAllByUserId(Long  user);
}
