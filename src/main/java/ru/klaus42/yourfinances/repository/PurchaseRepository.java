package ru.klaus42.yourfinances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klaus42.yourfinances.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
}
