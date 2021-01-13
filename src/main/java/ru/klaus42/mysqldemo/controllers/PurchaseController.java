package ru.klaus42.mysqldemo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.klaus42.mysqldemo.entity.Currency;
import ru.klaus42.mysqldemo.entity.Purchase;
import ru.klaus42.mysqldemo.entity.PurchaseItem;
import ru.klaus42.mysqldemo.entity.User;
import ru.klaus42.mysqldemo.repository.CurrencyRepository;
import ru.klaus42.mysqldemo.repository.PurchaseItemRepository;
import ru.klaus42.mysqldemo.repository.PurchaseRepository;
import ru.klaus42.mysqldemo.repository.UserRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("user/purchase")
public class PurchaseController {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    PurchaseItemRepository purchaseItemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CurrencyRepository currencyRepository;


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

        return "user/purchase";
    }

    @PostMapping("/add")
    public String addPurchase(@Valid Purchase purchase, Model model, Authentication authentication, Errors errors) {
        User user = userRepository.findByUsername(authentication.getName());

        if (user == null) return "user/purchase";

        purchase.setUser(user);

        purchaseRepository.save(purchase);

        return "redirect:/user/purchase";
    }

    @GetMapping("/add")
    public String returnToPurchase() {
        return "redirect:/user/purchase";
    }

    @GetMapping("/{id}/items")
    public String getPurchaseItems(@PathVariable("id") Long id,Model model, Authentication authentication) {

        Purchase purchase = purchaseRepository.findById(id).get();

        List<PurchaseItem> purchaseItems =purchase.getPurchaseItems();

        PurchaseItem newPurchaseItem = new PurchaseItem();

        model.addAttribute("purchaseItems", purchaseItems);
        model.addAttribute("purchase", purchase);
        model.addAttribute("purchaseItem", newPurchaseItem);
        model.addAttribute("total",purchaseItemRepository.total(purchase.getId()));


        return "user/purchase/purchaseitems";
    }

    @PostMapping("/{id}/additem")
    public String addPurchaseItem(@Valid PurchaseItem purchaseItem,@PathVariable("id") Long id, Model model, Authentication authentication, Errors errors) {
        User user = userRepository.findByUsername(authentication.getName());

        if (user == null) return "user/purchase";

        purchaseItem.setPurchase(purchaseRepository.findById(id).get());

        purchaseItemRepository.save(purchaseItem);

        return "redirect:/user/purchase/"+id+"/items";
    }


}
