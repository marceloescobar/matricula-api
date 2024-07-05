package com.mescobar.matricula.curso.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AulaDTO(
        int id,
        @NotNull @NotBlank @Size(min = 5, max = 100) String nome,
        @NotNull @NotBlank @Size(min = 10, max = 11) String url
) {
}
