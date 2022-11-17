package com.api.teaeduc.models;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "PESSOA")
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PESSOA", nullable = false, unique = true, length = 100)
    private Long id;

    @Column(name = "NOME")
    private String nome;
}
