package com.api.teaeduc.dtos;

import java.util.List;

import com.api.teaeduc.models.enumeration.TipoExercicioEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExercicioDTO extends BaseDTO {
    private Long id;
    private Long idAtividade;
    private TipoExercicioEnum tipoExercicio;
    private List<ImagensExerciciosDTO> imagensExercicio;
    private String palavra;
    private List<String> palavraList;
    private String nomeImagem;
    private String nomeParabenizacao;
}
