package com.api.teaeduc.dtos;

import com.api.teaeduc.models.enumeration.TipoAtividadeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtividadeFiltroDTO extends FiltroDTO {
    private Long id;
    private Long idProfessor;
    private TipoAtividadeEnum tipoAtividade;
    private String nomeAtividade;
    private Long qtdAtividade;
    private Boolean tipoLetra;
    private Boolean tipoSilaba;
    private Boolean tipoImagem;
}
