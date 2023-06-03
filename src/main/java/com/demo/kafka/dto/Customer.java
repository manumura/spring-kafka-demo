package com.demo.kafka.dto;

import jakarta.validation.constraints.NotNull;

public record Customer(@NotNull Long id, String name, Integer age) {
}
