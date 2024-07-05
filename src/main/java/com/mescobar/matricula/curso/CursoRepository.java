package com.mescobar.matricula.curso;

import com.mescobar.matricula.curso.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	Page<Curso> findByStatus(Pageable pageable, StatusEnum status);

	Page<Curso> findByNomeContaining(String nome, Pageable pageable);
}
