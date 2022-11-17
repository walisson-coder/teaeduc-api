package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtividadeAlunoFiltroDTO extends FiltroDTO {
    private Long id;
    private Long idAtividade;
    private Long idAluno;
    private Boolean concluido;
    private String nomeAtividade;
    private String tipoConcluido;
}
