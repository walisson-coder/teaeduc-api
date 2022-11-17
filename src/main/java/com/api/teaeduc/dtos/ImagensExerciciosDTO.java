package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImagensExerciciosDTO extends BaseDTO {
    private Long id;
    private Long idExercicio;
    private Long idAluno;
    private String tipo;
    private String pathImagem;
}
