package com.example.kojinkaihatu.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginForm {
    @NotEmpty
    private String loginId;
    @NotEmpty(message = "パスワードは必須入力です")
    @Length(min = 1, max = 30)
    private String password;
}
