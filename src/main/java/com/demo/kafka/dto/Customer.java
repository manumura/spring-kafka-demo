package com.demo.kafka.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Customer {

    @NotNull
    private Long id;
    private String name;
    private Integer age;
}
