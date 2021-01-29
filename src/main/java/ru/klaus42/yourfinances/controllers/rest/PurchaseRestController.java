package ru.klaus42.yourfinances.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.klaus42.yourfinances.entity.Purchase;
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

    public PurchaseRestController(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping("/user/purchases")
    public List<Purchase> geUserPurchases(Principal principal) {
        return purchaseRepository.findAllByUserId(this.getUserByAuth(principal).getId());
    }

    @PostMapping("/user/purchase")
    public Purchase updatePurchase(@RequestBody @Valid Purchase purchase,Principal principal){
        purchase.setUser(this.getUserByAuth(principal));
        return purchaseRepository.save(purchase);
    }

    @DeleteMapping("/user/purchase")
    public Boolean deletePurchase(@RequestParam Long id, Principal principal){

        User user = this.getUserByAuth(principal);

        Purchase purchaseToDelete = purchaseRepository.findById(id).get();

        if (user.getPurchases().contains(purchaseToDelete)){
            System.out.println(purchaseToDelete.getName());
            purchaseRepository.delete(purchaseToDelete);
            return true;
        }

        System.out.println(purchaseToDelete);
        return false;
    }

    private User getUserByAuth( Principal principal) {
        User user = new User();
        String userName =  principal.getName();
        if (userName != null) {
            user = userRepository.findByUsername(userName);
        }

        return user.getName() != null ? user : null;
    }



}
