package ru.klaus42.yourfinances.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class PurchaseTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @OneToOne
    @JoinColumn(name = "cash_id")
//    @JsonBackReference(value = "cash")
    private Cash cash;

    @NotNull
//    @JsonBackReference(value = "amount")
    private Float amount;
}
