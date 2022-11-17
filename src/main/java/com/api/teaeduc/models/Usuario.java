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
@Table(name = "USUARIO")
public class Usuario implements BaseEntity, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_USUARIO", nullable = false, unique = true, length = 100)
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SENHA")
    private String senha;

    @Column(name = "DT_ULTIMO_ACESSO")
    private Date dataAcesso;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "ATIVO")
    private Boolean ativo;
}
