package com.api.teaeduc.dtos;

import com.api.teaeduc.models.enumeration.TipoExercicioEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExercicioFiltroDTO extends FiltroDTO {
    private Long id;
    private Long idAtividade;
    private TipoExercicioEnum tipoExercicio;
}