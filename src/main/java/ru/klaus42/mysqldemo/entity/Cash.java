package ru.klaus42.mysqldemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Cash {
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
