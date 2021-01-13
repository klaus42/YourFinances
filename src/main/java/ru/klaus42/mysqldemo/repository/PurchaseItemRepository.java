package ru.klaus42.mysqldemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.klaus42.mysqldemo.entity.PurchaseItem;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem,Long> {
    @Query(value = "SELECT sum(quantity * price) FROM purchase_item where purchase_id=?1", nativeQuery = true)
    Float total(Long id);
}
