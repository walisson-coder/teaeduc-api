package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImagensExerciciosFiltroDTO extends FiltroDTO {
    private Long id;
    private Long idExercicio;
    private Long idAluno;
}