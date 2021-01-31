package ru.klaus42.yourfinances.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long id;

    @OneToOne
    @JoinColumn(name = "purchase_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Purchase purchase;

    @OneToOne
    @JoinColumn(name = "cash_id")

//    @JsonBackReference(value = "cash")
    @NotNull
    private Cash cash;

    @NotNull
//    @JsonBackReference(value = "amount")
    private Float amount;
}
