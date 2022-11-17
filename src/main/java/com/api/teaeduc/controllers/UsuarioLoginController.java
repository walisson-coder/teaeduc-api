package com.api.teaeduc.controllers;

import com.api.teaeduc.application.UsuarioApplication;
import com.api.teaeduc.controllers.base.BaseController;
import com.api.teaeduc.dtos.JwtDTO;
import com.api.teaeduc.dtos.UsuarioDTO;
import com.api.teaeduc.utils.BusinessException;
import com.api.teaeduc.utils.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${privateApi}/auth")
@CrossOrigin(origins = { "*" })
public class UsuarioLoginController extends BaseController {
	
	@Autowired
	private UsuarioApplication usuarioLoginApplication;

	@PostMapping("/refresh-token")
	public ResponseEntity<Object> refreshToken(@RequestHeader("authorization") String authorization) throws BusinessException, JsonProcessingException {
		String username = JWTUtil.getUsername(authorization);
		UsuarioDTO usuarioDTO = usuarioLoginApplication.buscarUserPorEmail(username);
		JwtDTO jwtToken = JWTUtil.refreshToken(authorization, usuarioDTO);
			
		return response(jwtToken);
	}
}
