package ru.klaus42.yourfinances.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@Table
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@JsonIdentityReference(alwaysAsId = true)
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Укажите название")
    private String name = "Новая часть покупки";

    @NotNull(message = "Укажите цену")
    private Double price;

    @Digits(integer=10, fraction=0, message = "Количество должно быть целым числом")
    private Short quantity = 1;

    @Transient
    private Double summaryPrice;

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    @JsonBackReference
    Purchase purchase;

    public Double getSummaryPrice() {
        return this.price * this.quantity;
    }
}
