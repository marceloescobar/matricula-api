package com.mescobar.matricula.curso.mapper;

import com.mescobar.matricula.curso.Aula;
import com.mescobar.matricula.curso.Curso;
import com.mescobar.matricula.curso.dto.AulaDTO;
import com.mescobar.matricula.curso.dto.CursoDTO;
import com.mescobar.matricula.curso.enums.CategoriaEnum;
import com.mescobar.matricula.curso.enums.StatusEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CursoMapper {

    public CursoDTO toDTO(Curso curso) {
        List<AulaDTO> aulas = curso.getAulas().stream()
                .map(aula -> new AulaDTO(aula.getId(), aula.getNome(), aula.getUrl()))
                .toList();

        return new CursoDTO(curso.getId(), curso.getNome(), curso.getCategoria().getDescricao(), aulas);
    }

    public Curso toEntity(CursoDTO cursoDTO) {
        Curso curso = Curso.builder()
                .id(cursoDTO.id())
                .nome(cursoDTO.nome())
                .status(StatusEnum.ATIVO)
                .categoria(CategoriaEnum.obtemCategoria(cursoDTO.categoria()))
                .build();

        Set<Aula> aulas = cursoDTO.aulas().stream().map(dto ->
                Aula.builder()
                        .id(dto.id())
                        .nome(dto.nome())
                        .url(dto.url())
                        .curso(curso)
                        .build()

        ).collect(Collectors.toSet());

        curso.setAulas(aulas);
        return curso;
    }

    public Aula toEntity(AulaDTO aulaDTO) {
        return Aula.builder()
                .id(aulaDTO.id())
                .nome(aulaDTO.nome())
                .url(aulaDTO.url())
                .build();
    }
}
