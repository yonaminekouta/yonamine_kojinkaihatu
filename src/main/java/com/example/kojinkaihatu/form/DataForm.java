package com.example.kojinkaihatu.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DataForm {
    @NotEmpty
    private Integer height;
    @NotEmpty
    private Integer weight;
}
