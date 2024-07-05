package com.mescobar.matricula.curso;

import com.mescobar.matricula.common.PaginaDTO;
import com.mescobar.matricula.curso.dto.CursoDTO;
import com.mescobar.matricula.curso.dto.FiltroCursoDTO;
import com.mescobar.matricula.curso.enums.CategoriaEnum;
import com.mescobar.matricula.curso.mapper.CursoMapper;
import com.mescobar.matricula.exception.RecordNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class CursoService {

    private final CursoRepository repository;
    private final CursoMapper mapper;

    public CursoService(CursoRepository repository, CursoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PaginaDTO<CursoDTO> listar(FiltroCursoDTO filtro) {
        Sort sort = Sort.by(Sort.Direction.fromString(filtro.getDirecaoOrdenacao()), filtro.getOrdenarPor());
        Pageable pagingSort = PageRequest.of(filtro.getPagina(), filtro.getTamanhoPagina(), sort);
        Page<Curso> pageCurso;

        if (filtro.getNome() == null)
            pageCurso = repository.findAll(pagingSort);
        else
            pageCurso = repository.findByNomeContaining(filtro.getNome(), pagingSort);

        List<CursoDTO> cursos = pageCurso.get().map(mapper::toDTO).toList();
        return new PaginaDTO<>(cursos, pageCurso.getTotalElements(), pageCurso.getTotalPages());
    }

    public CursoDTO findById(Long id) {
        return this.repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CursoDTO salvar(@Valid @NotNull CursoDTO cursoDTO) {
        return mapper.toDTO(this.repository.save(mapper.toEntity(cursoDTO)));
    }

    public CursoDTO alterar(@NotNull @Positive Long id, @Valid CursoDTO cursoDTO) {
        Curso cursoSalvo = this.buscaPorID(id);
        cursoSalvo.setNome(cursoDTO.nome());
        cursoSalvo.setCategoria(CategoriaEnum.obtemCategoria(cursoDTO.categoria()));
        this.mergeAulaParaAtualizacao(cursoSalvo, cursoDTO);

        return mapper.toDTO(repository.save(cursoSalvo));
    }

    private void mergeAulaParaAtualizacao(Curso cursoAtualizado, CursoDTO courseRequestDTO) {
        List<Aula> aulasRemover = cursoAtualizado.getAulas().stream()
                .filter(aula -> courseRequestDTO.aulas().stream().filter(e -> e.id() != 0)
                        .noneMatch(lessonDto -> lessonDto.id() == aula.getId()))
                .toList();

        aulasRemover.forEach(cursoAtualizado::removerAula);

        courseRequestDTO.aulas().forEach(aulaDTO -> {
            // sem id, deve add nova aula
            if (aulaDTO.id() == 0) {
                cursoAtualizado.addAula(mapper.toEntity(aulaDTO));
            } else {
                // existing lesson, find it and update
                cursoAtualizado.getAulas().stream()
                        .filter(lesson -> lesson.getId() == aulaDTO.id())
                        .findAny()
                        .ifPresent(lesson -> {
                            lesson.setNome(aulaDTO.nome());
                            lesson.setUrl(aulaDTO.url());
                        });
            }
        });
    }

    public void deleteCurso(@NotNull @Positive Long id) {
        repository.delete(this.buscaPorID(id));
    }

    private Curso buscaPorID(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

}
