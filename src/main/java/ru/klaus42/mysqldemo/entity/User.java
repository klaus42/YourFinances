package ru.klaus42.mysqldemo.entity;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.klaus42.mysqldemo.components.EmailValid;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

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
    @Column(unique=true)
    @Size(min = 5, max = 100)
    private String name;

    @NotBlank(message = "Введите адрес вашей электронной почты")
    @NotNull
    @Column(unique = true)
    @Size(min = 7, max = 100)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email имеет неверный формат")
    @EmailValid
    private String email;

    @NotBlank(message = "Введите пароль")
    @Size(min = 6,message = "Длина пароля не менее 6 символов")
    private String password;

    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserProfile profile;

    @OneToMany(mappedBy="user")
    private Set<Cash> Cash;

}
