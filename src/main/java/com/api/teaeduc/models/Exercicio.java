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
import javax.persistence.Table;

import com.api.teaeduc.models.enumeration.TipoExercicioEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "exercicio_ltr_slb")
public class Exercicio implements BaseEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_EXERCICIO", nullable = false, unique = true, length = 100)
    private Long id;

    @Column(name = "ID_ATIVIDADE", nullable = false)
    private Long idAtividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ATIVIDADE", insertable = false, updatable = false)
    private Atividade atividade;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_EXERCICIO", nullable = false)
    private TipoExercicioEnum tipoExercicio;

    @Column(name = "PALAVRA_BASE", nullable = false)
    private String palavra;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "exercicio", targetEntity = ImagensExercicios.class)
    private List<ImagensExercicios> imagensExercicio;
}