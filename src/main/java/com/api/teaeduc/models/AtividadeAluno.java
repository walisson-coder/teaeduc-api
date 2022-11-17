package com.api.teaeduc.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.api.teaeduc.models.enumeration.TipoAtividadeEnum;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "atividade_aluno")
public class AtividadeAluno implements BaseEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ATIVIDADE_ALUNO", nullable = false, unique = true, length = 100)
    private Long id;

    @Column(name = "ID_ATIVIDADE")
    private Long idAtividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ATIVIDADE", insertable = false, updatable = false)
    private Atividade atividade;

    @Column(name = "ID_ALUNO")
    private Long idAluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ALUNO", insertable = false, updatable = false)
    private Aluno aluno;

    @Column(name = "CONCLUIDO")
    private Boolean concluido;
}
