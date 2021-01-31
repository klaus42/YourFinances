package ru.klaus42.yourfinances.controllers.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.klaus42.yourfinances.entity.Purchase;
import ru.klaus42.yourfinances.entity.PurchaseItem;
import ru.klaus42.yourfinances.repository.PurchaseItemRepository;
import ru.klaus42.yourfinances.repository.PurchaseRepository;
import ru.klaus42.yourfinances.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PurchaseItemRestController {

    @Autowired
    PurchaseItemRepository purchaseItemRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/purchase/{id}/getitems")
    public List<PurchaseItem> getPurchaseItemList(@PathVariable Long id) {
        return purchaseItemRepository.getAllByPurchaseId(id).get();
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
}
