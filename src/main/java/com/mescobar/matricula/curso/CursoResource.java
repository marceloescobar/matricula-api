package com.mescobar.matricula.curso;

import com.mescobar.matricula.curso.dto.CursoDTO;
import com.mescobar.matricula.common.PaginaDTO;
import com.mescobar.matricula.curso.dto.FiltroCursoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/cursos")
//@CrossOrigin(origins = "http://localhost:8081")
public class CursoResource {

    private final CursoService cursoService;

    public CursoResource(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public PaginaDTO<CursoDTO> listar(
            @RequestParam(defaultValue = "0") @PositiveOrZero int pagina,
            @RequestParam(defaultValue = "10") @Positive @Max(100) int tamanhoPagina,
            @RequestParam(defaultValue = "id") String ordenarPor,
            @RequestParam(defaultValue = "ASC") @Pattern(regexp = "asc|desc") String direcaoOrdenacao,
            @RequestParam(required = false) String nome) {
        return cursoService.listar(FiltroCursoDTO.builder()
                .nome(nome)
                .pagina(pagina)
                .tamanhoPagina(tamanhoPagina)
                .ordenarPor(ordenarPor)
                .direcaoOrdenacao(direcaoOrdenacao)
                .build());
    }

    @GetMapping("/{id}")
    public CursoDTO obterCursoById(@PathVariable("id") @NotNull @Positive Long id) {
        return cursoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CursoDTO criarCurso(@RequestBody @Valid @NotNull CursoDTO cursoDTO) {
        return cursoService.salvar(cursoDTO);
    }

    @PutMapping("/{id}")
    public CursoDTO alterarCurso(@PathVariable("id") @NotNull @Positive Long id,
            @RequestBody @Valid @NotNull CursoDTO cursoDTO) {
        return cursoService.alterar(id, cursoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCurso(@PathVariable("id") @NotNull @Positive Long id) {
        cursoService.deleteCurso(id);
    }

}
