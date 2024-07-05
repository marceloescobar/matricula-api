package com.mescobar.matricula.curso.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum StatusEnum {

    ATIVO("Ativo"),
    INATIVO("Inativo");

    private final String descricao;

    private StatusEnum(String descricao) {
        this.descricao = descricao;
    }
}
