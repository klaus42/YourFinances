package ru.klaus42.yourfinances.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name = "Новая покупка";

    @Column
    //Посмотреть различия с обычным
    //https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-type-conversions.html
    //Используем, чтобы не городить форматирование java.util.Date
    //MySQL не может напрямую сохранить java.util.Date
    private java.sql.Timestamp purchaseDate;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    @JsonManagedReference
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    User user;

    @OneToMany(mappedBy="purchase")
    private List<PurchaseItem> purchaseItems;
}
