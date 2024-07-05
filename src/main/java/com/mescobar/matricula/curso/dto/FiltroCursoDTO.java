package com.mescobar.matricula.curso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FiltroCursoDTO {
    private final int pagina;
    private final int tamanhoPagina;
    private final String nome;
    private final String ordenarPor;
    private final String direcaoOrdenacao;
}
