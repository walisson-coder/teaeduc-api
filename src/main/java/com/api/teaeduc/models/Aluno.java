package com.api.teaeduc.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "aluno")
public class Aluno implements BaseEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ALUNO", nullable = false, unique = true, length = 100)
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "ID_PROFESSOR")
    private Long idProfessor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROFESSOR", insertable = false, updatable = false)
    private Professor professor;

    @Column(name = "ID_INSTITUICAO")
    private Long idInstituicao;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CPF")
    private Long cpf;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aluno", targetEntity = AtividadeAluno.class)
    @OrderBy("id")
    private List<AtividadeAluno> atividadesAluno;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aluno", targetEntity = ImagensExercicios.class)
    @OrderBy("id")
    private List<ImagensExercicios> imagensExercicios;
}
