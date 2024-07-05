package com.mescobar.matricula.curso.enums.converter;

import com.mescobar.matricula.curso.enums.CategoriaEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoriaConverter implements AttributeConverter<CategoriaEnum, String> {
    @Override
    public String convertToDatabaseColumn(CategoriaEnum attribute) {
        return attribute != null ? attribute.getDescricao() : null;
    }

    @Override
    public CategoriaEnum convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        return CategoriaEnum.obtemCategoria(value);
    }
}
