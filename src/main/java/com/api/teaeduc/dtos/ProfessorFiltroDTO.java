package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorFiltroDTO extends FiltroDTO {
    private Long id;
    private String nome;
    private Long cpf;
    private String email;
    private String telefone;
    private Long idInstituicao;
}
