package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoFiltroDTO extends FiltroDTO {
    private Long id;
    private String nome;
    private String email;
    private Long idInstituicao;
    private Long idProfessor;

}
