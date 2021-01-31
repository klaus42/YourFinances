package ru.klaus42.yourfinances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.klaus42.yourfinances.entity.PurchaseTransaction;

public interface PurchaseTransactionRepository extends JpaRepository<PurchaseTransaction,Long> {
    @Query(value = "SELECT  COALESCE(sum(amount),0) as total FROM purchase_transaction where purchase_id=?1", nativeQuery = true)
    Float sumByPurchaseId(Long purchaseId);
}
