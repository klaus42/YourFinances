package ru.klaus42.mysqldemo.entity;

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

    private Integer price;

    private Short count;


    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    Purchase purchase;
}
