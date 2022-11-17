package com.api.teaeduc.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String login;
	private String senha;
    private String tipo;
}
