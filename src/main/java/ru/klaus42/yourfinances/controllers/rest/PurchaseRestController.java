package ru.klaus42.yourfinances.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.klaus42.yourfinances.entity.Purchase;
import ru.klaus42.yourfinances.entity.PurchaseTransaction;
import ru.klaus42.yourfinances.entity.User;
import ru.klaus42.yourfinances.repository.PurchaseRepository;
import ru.klaus42.yourfinances.repository.UserRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
public class PurchaseRestController {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    UserRepository userRepository;

    public PurchaseRestController(PurchaseRepository purchaseRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
    }

    //Получаем список покупок пользователя
    @GetMapping("/user/purchases")
    public List<Purchase> geUserPurchases(Principal principal) {
        return purchaseRepository.findAllByUserId(this.getUserByAuth(principal).getId());
    }

    @GetMapping("/user/purchase/{id}")
    public Purchase getUserPurchaseById(@PathVariable Long id, Principal principal) {
        Purchase purchase =  purchaseRepository.findById(id).orElse(null);
        return purchase;
    }

    //Обновляем покупку
    @PostMapping("/user/purchase")
    public Purchase updatePurchase(@Valid @RequestBody Purchase purchase, Principal principal, Errors errors) {
        User currentUser = this.getUserByAuth(principal);
        Purchase presentPurchase = purchaseRepository.findOneByUserIdAndId(currentUser.getId(), purchase.getId()).orElse(null);
        if (presentPurchase != null) {
            purchase.setUser(currentUser);
            return purchaseRepository.save(purchase);
        }
        return null;

    }

    @DeleteMapping("/user/purchase")
    public Boolean deletePurchase(@RequestParam Long id, Principal principal) {

        User user = this.getUserByAuth(principal);

        Purchase purchaseToDelete = purchaseRepository.findById(id).get();

        if (user.getPurchases().contains(purchaseToDelete)) {
            System.out.println(purchaseToDelete.getName());
            purchaseRepository.delete(purchaseToDelete);
            return true;
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
