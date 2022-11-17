package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstituicaoDTO extends BaseDTO {
    private Long id;
    private String nome;
    private Long cnpj;
    private String email;
    private Long cep;
    private String endereco;
    private Long numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String telefone;
    private String senha;
    private String tipo;
    private int qtdProfessor;
}
