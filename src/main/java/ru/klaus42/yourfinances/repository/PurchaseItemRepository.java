package ru.klaus42.yourfinances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.klaus42.yourfinances.entity.PurchaseItem;

import java.util.List;
import java.util.Optional;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem,Long> {
    @Query(value = "SELECT  COALESCE(sum(quantity * price),0) FROM purchase_item where purchase_id=?1", nativeQuery = true)
    Float total(Long id);

    Optional<List<PurchaseItem>> getAllByPurchaseId(Long id);
}
