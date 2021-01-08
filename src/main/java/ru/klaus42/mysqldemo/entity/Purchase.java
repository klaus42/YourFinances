package ru.klaus42.mysqldemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
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
