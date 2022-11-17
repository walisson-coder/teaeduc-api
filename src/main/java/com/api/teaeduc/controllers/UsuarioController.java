package com.api.teaeduc.controllers;

import java.util.Date;

import com.api.teaeduc.application.AlunoApplication;
import com.api.teaeduc.application.InstituicaoApplication;
import com.api.teaeduc.application.ProfessorApplication;
import com.api.teaeduc.application.UsuarioApplication;
import com.api.teaeduc.controllers.base.BaseController;
import com.api.teaeduc.dtos.AlunoDTO;
import com.api.teaeduc.dtos.AlunoFiltroDTO;
import com.api.teaeduc.dtos.InstituicaoDTO;
import com.api.teaeduc.dtos.InstituicaoFiltroDTO;
import com.api.teaeduc.dtos.JwtDTO;
import com.api.teaeduc.dtos.LoginDTO;
import com.api.teaeduc.dtos.ProfessorDTO;
import com.api.teaeduc.dtos.ProfessorFiltroDTO;
import com.api.teaeduc.dtos.UsuarioDTO;
import com.api.teaeduc.dtos.UsuarioFiltroDTO;
import com.api.teaeduc.utils.BusinessException;

import com.api.teaeduc.utils.JWTUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("${privateApi}/usuarios")
public class UsuarioController extends BaseController {
    @Autowired
	UsuarioApplication usuarioApplication;

    @Autowired
	ProfessorApplication professorApplication;

    @Autowired
	AlunoApplication alunoApplication;

    @Autowired
	InstituicaoApplication instituicaoApplication;

    @PostMapping
    public ResponseEntity<Object> saveUsuario(@RequestBody UsuarioDTO dto){
        try {
            dto.setDataAcesso(new Date());
            dto.setAtivo(true);
            return response(this.usuarioApplication.salvar(dto));
        } catch (Exception e) {
            return response(e);
        }
    }

    @GetMapping("/meus-dados")
    public ResponseEntity<Object> usuarioLogado(@RequestHeader("authorization") String authorization) {
        try {
            String userName = this.usuarioApplication.buscarUsernameUsuarioLogado();
            UsuarioDTO usuarioDTO = this.usuarioApplication.buscarUserPorEmail(userName);

            if(usuarioDTO.getTipo().equals("PROFESSOR")) {
                ProfessorFiltroDTO professorFiltro = new ProfessorFiltroDTO();
                professorFiltro.setEmail(usuarioDTO.getEmail());
                ProfessorDTO professorDTO = this.professorApplication.buscarFiltrado(professorFiltro).get(0);
                professorDTO.setQtdAlunos(alunoApplication.countFiltrado(professorDTO.getId()));
                professorDTO.setTipo(usuarioDTO.getTipo());
                return response(professorDTO);
            }

            if(usuarioDTO.getTipo().equals("ALUNO")) {
                AlunoFiltroDTO alunoFiltro = new AlunoFiltroDTO();
                alunoFiltro.setEmail(usuarioDTO.getEmail());
                AlunoDTO alunoDTO = this.alunoApplication.buscarFiltrado(alunoFiltro).get(0);
                alunoDTO.setTipo(usuarioDTO.getTipo());
                return response(alunoDTO);
            }

            if(usuarioDTO.getTipo().equals("INSTITUICAO")) {
                InstituicaoFiltroDTO instituicaoFiltro = new InstituicaoFiltroDTO();
                instituicaoFiltro.setEmail(usuarioDTO.getEmail());
                InstituicaoDTO instituicaoDTO = this.instituicaoApplication.buscarFiltrado(instituicaoFiltro).get(0);
                instituicaoDTO.setTipo(usuarioDTO.getTipo());
                instituicaoDTO.setQtdProfessor(professorApplication.countFiltrado(instituicaoDTO.getId()));
                return response(instituicaoDTO);
            }

            return response(usuarioDTO);
            
        } catch (Exception e) {
            return response(e);
        }
	}
}
