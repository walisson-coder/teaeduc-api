package com.api.teaeduc.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PessoatDto {

    @NotBlank
    private String nome;

}
