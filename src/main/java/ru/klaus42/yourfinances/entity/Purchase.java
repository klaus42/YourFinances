package ru.klaus42.yourfinances.entity;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@JsonIdentityReference(alwaysAsId = true)
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
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy hh:mm",locale = "ru_RU")
    private java.sql.Timestamp purchaseDate;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user")
    User user;

    @OneToMany(mappedBy="purchase",cascade = CascadeType.ALL)
    //@JsonBackReference
    @JsonBackReference
    private List<PurchaseItem> purchaseItems;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    //@JsonManagedReference(value = "currency")
    private Currency currency;


}
