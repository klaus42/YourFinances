package ru.klaus42.mysqldemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.klaus42.mysqldemo.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
}
