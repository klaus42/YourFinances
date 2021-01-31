package ru.klaus42.yourfinances.controllers.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.klaus42.yourfinances.entity.*;
import ru.klaus42.yourfinances.repository.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class PurchaseItemRestController {

    @Autowired
    PurchaseItemRepository purchaseItemRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    PurchaseTransactionRepository purchaseTransactionRepository;

    @Autowired
    CashRepository cashRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/purchase/{id}/getitems")
    public List<PurchaseItem> getPurchaseItemList(@PathVariable Long id) {
        return purchaseItemRepository.getAllByPurchaseId(id).orElse(null);
    }

    @PostMapping("/user/purchase/{id}/updateitem")
    public PurchaseItem updatePurchaseItem(@Valid @RequestBody PurchaseItem purchaseItem,
                                           Errors errors, @PathVariable Long id, Model model, Authentication authentication) {

        if (null != errors && errors.getErrorCount() > 0) {
            return null;
        }
        Purchase purchase = purchaseRepository.findById(id).get();
        purchaseItem.setPurchase(purchase);
        return purchaseItemRepository.save(purchaseItem);
    }

    @DeleteMapping("/user/purchase/{id}/deleteitem")
    public boolean deletePurchaseItem(@RequestParam Long itemId, @PathVariable Long id, Principal principal){
        User user = this.getUserByAuth(principal);

        PurchaseItem purchaseItem = purchaseItemRepository.findById(id).orElse(null);

        if(user.getPurchases().contains(purchaseItem.getPurchase())){
            purchaseItemRepository.delete(purchaseItem);
            return true;
        }

        return false;
    }

    @GetMapping("/user/purchase/{id}/gettransactions")
    public List<PurchaseTransaction> getPurchaseTransactions(@PathVariable Long id) {
        return purchaseRepository.findById(id).get().getPurchaseTransactions();
    }

    @DeleteMapping("/user/purchase/{id}/deletetransaction")
    public Boolean deleteTransaction(@RequestParam Long transactionId, Principal principal) {

        User user = this.getUserByAuth(principal);
        PurchaseTransaction transactionToDelete = purchaseTransactionRepository.findById(transactionId).orElse(null);
        Purchase purchaseWithTransaction = transactionToDelete.getPurchase();

        if (user.getPurchases().contains(purchaseWithTransaction)) {

            Cash userCash = transactionToDelete.getCash();
            userCash.setAmount(userCash.getAmount() + transactionToDelete.getAmount());

            try {
                purchaseTransactionRepository.delete(transactionToDelete);
                cashRepository.save(userCash);
                return true;
            } catch (DataAccessException e) {
                return false;
            }
        }
        return false;
    }

    private User getUserByAuth(Principal principal) {
        User user = new User();
        String userName = principal.getName();
        if (userName != null) {
            user = userRepository.findByUsername(userName);
        }

        return user.getName() != null ? user : null;
    }
}
