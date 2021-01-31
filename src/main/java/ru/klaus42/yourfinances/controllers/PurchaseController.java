package ru.klaus42.yourfinances.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.klaus42.yourfinances.entity.*;
import ru.klaus42.yourfinances.repository.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private PurchaseTransactionRepository purchaseTransactionRepository;


    @GetMapping
    public String getAllPurchases(Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());

        List<Purchase> purchases = user.getPurchases();

        Currency rub = currencyRepository.findByName("RUB");

        List<Currency> currencies = new ArrayList();
        currencies.add(rub);

        Purchase newPurchase = new Purchase();
        newPurchase.setCurrency(rub);
        //newPurchase.setPurchaseDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));

        model.addAttribute(user);
        model.addAttribute("purchases", purchases);
        model.addAttribute("purchase", newPurchase);
        model.addAttribute("currencies", currencies);

        return "user/purchase/purchase";
    }

    @PostMapping("/add")
    public String addPurchase(@Valid Purchase purchase, Model model, Authentication authentication, Errors errors) {
        User user = userRepository.findByUsername(authentication.getName());

        if (user == null) return "user/purchase/purchase";

        if (errors.hasErrors()) {
            return "user/purchase/purchase";
        }

        purchase.setUser(user);

        purchaseRepository.save(purchase);

        return "redirect:/user/purchase";
    }

    @GetMapping("/add")
    public String returnToPurchase() {
        return "redirect:/user/purchase";
    }

    @GetMapping("/{id}/items")
    public String getPurchaseItems(@PathVariable("id") Long id, Model model, Authentication authentication) {

        Purchase purchase = purchaseRepository.findById(id).get();

        List<PurchaseItem> purchaseItems = purchase.getPurchaseItems();

        PurchaseItem newPurchaseItem = new PurchaseItem();

        Float total = purchaseItemRepository.total(purchase.getId());
        Float totalToPay = calculateSumToPay(purchase.getId());

        PurchaseTransaction purchaseTransaction = new PurchaseTransaction();
        purchaseTransaction.setPurchase(purchase);
        purchaseTransaction.setAmount(totalToPay);

        model.addAttribute("purchaseItems", purchaseItems);
        model.addAttribute("purchase", purchase);
        model.addAttribute("purchaseItem", newPurchaseItem);
        model.addAttribute("transaction", purchaseTransaction);

        model.addAttribute("total", total);


        return "user/purchase/purchaseitems";
    }

    @PostMapping("/{id}/additem")
    public String addPurchaseItem(@Valid PurchaseItem purchaseItem, Errors errors, @PathVariable("id") Long id, Model model, Authentication authentication) {

        if (null != errors && errors.getErrorCount() > 0) {

            Purchase purchase = purchaseRepository.findById(id).get();
            Float total = purchaseItemRepository.total(purchase.getId());
            Float totalToPay = calculateSumToPay(purchase.getId());

            PurchaseTransaction purchaseTransaction = new PurchaseTransaction();
            purchaseTransaction.setPurchase(purchase);
            purchaseTransaction.setAmount(totalToPay);

            model.addAttribute("purchaseItems", purchase.getPurchaseItems());
            model.addAttribute("purchase", purchase);
            model.addAttribute("transaction", purchaseTransaction);
            model.addAttribute("total", total);


            return "user/purchase/purchaseItems";
        }
        User user = userRepository.findByUsername(authentication.getName());

        if (user == null) return "user/purchase/purchase";

        purchaseItem.setPurchase(purchaseRepository.findById(id).get());

        purchaseItemRepository.save(purchaseItem);

        return "redirect:/user/purchase/" + id + "/items";
    }


    @PostMapping("/{id}/addtransaction")
    public String addTransaction(@Valid PurchaseTransaction purchaseTransaction, Errors errors, @PathVariable("id") Long id, Model model, Authentication authentication) {

        if (null != errors && errors.getErrorCount() > 0) {

            Purchase purchase = purchaseRepository.findById(id).get();
            Float total = purchaseItemRepository.total(purchase.getId());
            Float totalToPay = calculateSumToPay(purchase.getId());

            model.addAttribute("purchaseItems", purchase.getPurchaseItems());
            model.addAttribute("purchase", purchase);
            model.addAttribute("total", total);


            return "user/purchase/purchaseItems";
        }
        User user = userRepository.findByUsername(authentication.getName());

        if (user == null) return "user/purchase/purchase";

        purchaseTransaction.setPurchase(purchaseRepository.findById(id).get());

        purchaseTransactionRepository.save(purchaseTransaction);

        return "redirect:/user/purchase/" + id + "/items";
    }

    private float calculateSumToPay(Long purchaseId) {
        Float total = purchaseItemRepository.total(purchaseId);
        Float payed = purchaseTransactionRepository.sumByPurchaseId(purchaseId);
        return total - payed;
    }


}
