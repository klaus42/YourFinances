package ru.klaus42.mysqldemo.entity;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.klaus42.mysqldemo.components.EmailValid;


import javax.persistence.*;
import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
//@org.springframework.data.relational.core.mapping.Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Заполните имя пользователя")
    @Size(min = 5, max = 100)
    private String name;

    @NotBlank(message = "Email is mandatory")
    @NotNull
    @NotEmpty
    @Column(unique = true)
    @Size(min = 7, max = 191)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email имеет неверный формат")
    @EmailValid
    private String email;

    private String password;
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserProfile profile;
}
