package com.api.teaeduc.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "imagens")
public class ImagensExercicios implements BaseEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_EXERCICIO_IMG", nullable = false, unique = true, length = 100)
    private Long id;

    @Column(name = "ID_EXERCICIO", nullable = true)
    private Long idExercicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EXERCICIO", insertable = false, updatable = false)
    private Exercicio exercicio;

    @Column(name = "ID_ALUNO", nullable = true)
    private Long idAluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALUNO", insertable = false, updatable = false)
    private Aluno aluno;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "PATH_IMAGEM")
    private String pathImagem;
}