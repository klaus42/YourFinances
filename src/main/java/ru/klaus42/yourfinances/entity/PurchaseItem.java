package ru.klaus42.yourfinances.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table
@NoArgsConstructor
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name = "Новая часть покупки";

    private Float price;


    private Short quantity;


    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    Purchase purchase;
}
