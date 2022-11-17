package com.api.teaeduc.controllers;

import java.util.Date;

import com.api.teaeduc.application.InstituicaoApplication;
import com.api.teaeduc.application.ProfessorApplication;
import com.api.teaeduc.application.UsuarioApplication;
import com.api.teaeduc.controllers.base.BaseController;
import com.api.teaeduc.dtos.InstituicaoDTO;
import com.api.teaeduc.dtos.InstituicaoFiltroDTO;
import com.api.teaeduc.dtos.ProfessorDTO;
import com.api.teaeduc.dtos.UsuarioDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("${privateApi}/instituicao")
public class InstituicaoController extends BaseController {
    @Autowired
	InstituicaoApplication instituicaoApplication;
    @Autowired
	UsuarioApplication usuarioApplication;
    @Autowired
    ProfessorApplication professorApplication;

    @PostMapping
    public ResponseEntity<Object> saveInstituicao(@RequestBody InstituicaoDTO dto){
        try {
            
            InstituicaoDTO dtoSaved =  this.instituicaoApplication.salvar(dto);
            if(dtoSaved != null){
                UsuarioDTO dtoUser = new UsuarioDTO();
                dtoUser.setDataAcesso(new Date());
                dtoUser.setAtivo(true);
                dtoUser.setEmail(dtoSaved.getEmail());
                dtoUser.setSenha(dto.getSenha());
                dtoUser.setTipo("INSTITUICAO");
                return response(this.usuarioApplication.salvar(dtoUser));
            }
            return response(dtoSaved);
        } catch (Exception e) {
            return response(e);
        }
    }


    @PostMapping(value="/salvarProfessor")
    public ResponseEntity<Object> saveProfessor(@RequestBody ProfessorDTO dto){
        try {
            ProfessorDTO dtoSaved =  this.professorApplication.salvar(dto);
            if(dtoSaved != null){
                UsuarioDTO dtoUser = new UsuarioDTO();
                dtoUser.setDataAcesso(new Date());
                dtoUser.setAtivo(true);
                dtoUser.setEmail(dtoSaved.getEmail());
                dtoUser.setSenha(dto.getSenha());
                dtoUser.setTipo("PROFESSOR");
                this.usuarioApplication.salvar(dtoUser);
                return response(dtoSaved);
            }
            return response(dtoSaved);
        } catch (Exception e) {
            return response(e);
        }
    }

    @GetMapping("")
    public ResponseEntity<Object> usuarioLogado(@RequestParam(value = "email") String email) {
        try {
            InstituicaoFiltroDTO filtro = new InstituicaoFiltroDTO();

            if(email != null && !email.equals("")){
                filtro.setEmail(email);
            }

            return response(this.instituicaoApplication.buscarFiltrado(filtro));
        } catch (Exception e) {
            return response(e);
        }
	}

    @GetMapping("/instituicao-professor")
    public ResponseEntity<Object> buscarInstituicaoByProfessor(@RequestHeader("authorization") String authorization,
                                                                @RequestParam(value = "email") String email) {
        try {
            InstituicaoFiltroDTO filtro = new InstituicaoFiltroDTO();

            if(email != null && !email.equals("")){
                filtro.setEmail(email);
            }

            return response(this.instituicaoApplication.buscarInstituicaoByProfessor(filtro));
        } catch (Exception e) {
            return response(e);
        }
	}
}
