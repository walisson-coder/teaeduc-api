package com.api.teaeduc.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "professor")
public class Professor implements BaseEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PROFESSOR", nullable = false, unique = true, length = 100)
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "CPF")
    private Long cpf;

    @Column(name = "ID_INSTITUICAO")
    private Long idInstituicao;

    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "TELEFONE")
    private String telefone;
}
