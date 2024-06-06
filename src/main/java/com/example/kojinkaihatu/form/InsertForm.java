package com.example.kojinkaihatu.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class InsertForm {
    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private Integer height;

    @NotEmpty
    private  Integer weight;

    @NotEmpty
    private Integer gender;
}
