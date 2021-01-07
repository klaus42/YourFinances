package ru.klaus42.mysqldemo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Cash {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(length=3)
    private String currency;

    private Integer amount = 0;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
