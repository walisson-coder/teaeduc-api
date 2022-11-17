package com.api.teaeduc.controllers;

import java.util.Date;

import com.api.teaeduc.application.AlunoApplication;
import com.api.teaeduc.application.ProfessorApplication;
import com.api.teaeduc.application.UsuarioApplication;
import com.api.teaeduc.controllers.base.BaseController;
import com.api.teaeduc.dtos.AlunoDTO;
import com.api.teaeduc.dtos.ProfessorDTO;
import com.api.teaeduc.dtos.ProfessorFiltroDTO;
import com.api.teaeduc.dtos.UsuarioDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("${privateApi}/professor")
public class ProfessorController extends BaseController {
    @Autowired
	ProfessorApplication professorApplication;
    @Autowired
	UsuarioApplication usuarioApplication;
    @Autowired
    AlunoApplication alunoApplication;

    @PostMapping
    public ResponseEntity<Object> saveProfessor(@RequestBody ProfessorDTO dto){
        try {
            
            ProfessorDTO dtoSaved =  this.professorApplication.salvar(dto);
            return response(dtoSaved);
        } catch (Exception e) {
            return response(e);
        }
    }

    @PostMapping(value="/salvarAluno")
    public ResponseEntity<Object> salveAluno(@RequestBody AlunoDTO dto){
        try {
            AlunoDTO dtoSaved =  this.alunoApplication.salvar(dto);
            if(dtoSaved != null){
                UsuarioDTO dtoUser = new UsuarioDTO();
                dtoUser.setDataAcesso(new Date());
                dtoUser.setAtivo(true);
                dtoUser.setEmail(dtoSaved.getEmail());
                dtoUser.setSenha(dto.getSenha());
                dtoUser.setTipo("ALUNO");
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
            ProfessorFiltroDTO filtro = new ProfessorFiltroDTO();

            if(email != null && !email.equals("")){
                filtro.setEmail(email);
            }

            return response(this.professorApplication.buscarFiltrado(filtro));
        } catch (Exception e) {
            return response(e);
        }
	}
}
