package com.api.teaeduc.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO extends BaseDTO {
    private Long id;
    private String email;
    private String senha;
    private Date dataAcesso;
    private String tipo;
    private Boolean ativo;
}
