package com.demo.kafka.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {

    @NotNull
    private Long id;
    private String name;
    private Integer age;
}
