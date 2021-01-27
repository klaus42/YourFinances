package ru.klaus42.yourfinances.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Currency {

    @Id
    @Column(name = "abc_code")
    private String name;

    @Size(max = 3)
    private Integer code;

    @Column(name = "name")
    private String displayName;

    private String country;
    private String comment;

    @OneToMany(mappedBy = "currency",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Cash> cash;


}
