package com.example.kojinkaihatu.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LogoutForm {
    @NotEmpty
    String password;
}
