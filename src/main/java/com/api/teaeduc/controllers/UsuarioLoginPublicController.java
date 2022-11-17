package com.api.teaeduc.controllers;

import com.api.teaeduc.application.UsuarioApplication;
import com.api.teaeduc.controllers.base.BaseController;
import com.api.teaeduc.dtos.JwtDTO;
import com.api.teaeduc.dtos.LoginDTO;
import com.api.teaeduc.dtos.UsuarioDTO;
import com.api.teaeduc.utils.BusinessException;
import com.api.teaeduc.utils.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin(origins = "*" )
@RequestMapping("${publicApi}/auth")
public class UsuarioLoginPublicController extends BaseController {

	@Autowired
	private UsuarioApplication usuarioLoginApplication;

	@PostMapping("/login")
	public ResponseEntity<Object> autenticarLogin(@RequestBody LoginDTO loginDTO) throws BusinessException, JsonProcessingException {
		UsuarioDTO usuarioDTO = usuarioLoginApplication.regraLogin(loginDTO);
		JwtDTO jwtToken = JWTUtil.encode(usuarioDTO);
			
		return response(jwtToken);
	}

}
