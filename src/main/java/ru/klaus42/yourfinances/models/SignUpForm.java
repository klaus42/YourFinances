package ru.klaus42.yourfinances.models;

import lombok.NoArgsConstructor;
import ru.klaus42.yourfinances.components.EmailValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
public class SignUpForm {

    @NotBlank(message = "Заполните имя пользователя")
    private String login;

    @EmailValid
    @Size(min = 7, max = 191)
    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
