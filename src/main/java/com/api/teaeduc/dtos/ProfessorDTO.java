package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorDTO extends BaseDTO {
    private Long id;
    private String nome;
    private Long cpf;
    private String email;
    private String telefone;
    private Long idInstituicao;
    private String senha;
    private String tipo;
    private int qtdAlunos;
    
}
