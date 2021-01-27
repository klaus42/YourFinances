package ru.klaus42.yourfinances.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Cash {

    //Вынести в emun
    public static final Short STATUS_ACTIVE = 10;
    public static final Short STATUS_DISABLED = 20;
    public static final Short STATUS_DELETED = 30;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "currency", nullable = false)
    @JsonManagedReference
    private Currency currency;

    private Integer amount = 0;

    private Short status = STATUS_ACTIVE;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}
