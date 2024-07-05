package com.mescobar.matricula.curso.dto;

import com.mescobar.matricula.curso.enums.CategoriaEnum;
import com.mescobar.matricula.curso.enums.validation.ValueOfEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CursoDTO(long id,
                       @NotNull @NotBlank @Size(min = 5, max = 100) String nome,
                       @NotNull @Size(max = 10) @ValueOfEnum(enumClass = CategoriaEnum.class) String categoria,
                       @NotNull @NotEmpty @Valid List<AulaDTO> aulas
) {
}
