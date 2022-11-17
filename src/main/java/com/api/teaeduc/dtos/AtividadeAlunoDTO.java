package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtividadeAlunoDTO extends BaseDTO {
    private Long id;
    private Long idAtividade;
    private Long idAluno;
    private String nomeAtividade;
    private Boolean concluido;
}
