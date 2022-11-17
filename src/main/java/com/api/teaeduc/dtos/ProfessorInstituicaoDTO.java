package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorInstituicaoDTO extends BaseDTO {
    private Long idProfessor;
    private Long idInstituicao;
    private Boolean status;
}
