package com.api.teaeduc.dtos;

import java.util.List;

import com.api.teaeduc.models.enumeration.TipoAtividadeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtividadeDTO extends BaseDTO {
    private Long id;
    private Long idProfessor;
    private TipoAtividadeEnum tipoAtividade;
    private String nomeAtividade;
    private Long qtdAtividade;
    private List<ExercicioDTO> exercicios;
}
