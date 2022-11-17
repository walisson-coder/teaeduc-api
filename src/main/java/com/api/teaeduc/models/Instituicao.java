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
@Table(name = "instituicao")
public class Instituicao implements BaseEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_INSTITUICAO", nullable = false, unique = true, length = 100)
    private Long id;
    @Column(name = "NOME")
    private String nome;

    @Column(name = "CNPJ")
    private Long cnpj;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CEP")
    private Long cep;

    @Column(name = "ENDERECO")
    private String endereco;

    @Column(name = "NUMERO")
    private Long numero;

    @Column(name = "BAIRRO")
    private String bairro;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "UF")
    private String uf;

    @Column(name = "TELEFONE")
    private String telefone;
}
