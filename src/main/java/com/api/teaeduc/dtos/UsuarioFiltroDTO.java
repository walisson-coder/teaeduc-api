package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioFiltroDTO extends FiltroDTO {
    private String email;
    private Long cpfCnpj;
    private String tipo;
}
