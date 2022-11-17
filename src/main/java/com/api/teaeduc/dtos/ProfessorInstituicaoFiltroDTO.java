package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorInstituicaoFiltroDTO extends FiltroDTO {
    private Long id;
    private Long idProfessor;
    private Long idInstituicao;
    private Boolean status;
}
