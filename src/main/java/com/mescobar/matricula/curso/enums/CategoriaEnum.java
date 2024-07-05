package com.mescobar.matricula.curso.enums;

import lombok.Getter;
import lombok.ToString;

import java.util.stream.Stream;

@Getter
public enum CategoriaEnum {
    BACKEND("Back-end"),
    FRONTEND("Front-end");

    private final String descricao;

    private CategoriaEnum(String descricao) {
        this.descricao = descricao;
    }

    public static CategoriaEnum obtemCategoria(String value) {
        return Stream.of(CategoriaEnum.values())
                .filter(c -> c.getDescricao().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
