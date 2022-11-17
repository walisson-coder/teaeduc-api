package com.api.teaeduc.models;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.api.teaeduc.models.enumeration.TipoAtividadeEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "atividade")
public class Atividade implements BaseEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ATIVIDADE", nullable = false, unique = true, length = 100)
    private Long id;

    @Column(name = "ID_PROFESSOR")
    private Long idProfessor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROFESSOR", insertable = false, updatable = false)
    private Professor professor;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_ATIVIDADE")
    private TipoAtividadeEnum tipoAtividade;

    @Column(name = "NOME_ATIVIDADE")
    private String nomeAtividade;

    @Column(name = "QTD_ATIVIDADE")
    private Long qtdAtividade;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "atividade", targetEntity = Exercicio.class)
    @OrderBy("id")
    private List<Exercicio> exercicios;
}
