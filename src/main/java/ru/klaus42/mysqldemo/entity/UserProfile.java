package ru.klaus42.mysqldemo.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table
@Getter
@Setter
@Entity
public class UserProfile {
    @Id
    @Column(name = "user_id")
    private Long id;


    @Column(nullable = true)
    private String phone;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
