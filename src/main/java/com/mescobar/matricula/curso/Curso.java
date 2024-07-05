package com.mescobar.matricula.curso;

import com.mescobar.matricula.curso.enums.CategoriaEnum;
import com.mescobar.matricula.curso.enums.StatusEnum;
import com.mescobar.matricula.curso.enums.converter.CategoriaConverter;
import com.mescobar.matricula.curso.enums.converter.StatusConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@EqualsAndHashCode
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "curso")
@SQLDelete(sql = "UPDATE curso set status = 'Inativo' where id =?")
@Where(clause = "status ='Ativo'")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(min = 5, max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "categoria", length = 10, nullable = false)
    @Convert(converter = CategoriaConverter.class)
    private CategoriaEnum categoria;

    @Column(name = "status", length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private StatusEnum status;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    private Set<Aula> aulas;

    public void addAula(Aula aula) {
        if (aula == null) {
            throw new IllegalArgumentException("Aula não pode ser null.");
        }
        aula.setCurso(this);
        this.aulas.add(aula);
    }

    public void removerAula(Aula aula) {
        if (aula == null) {
            throw new IllegalArgumentException("Aula não pode ser null");
        }
        aula.setCurso(null);
        this.aulas.remove(aula);
    }

}
