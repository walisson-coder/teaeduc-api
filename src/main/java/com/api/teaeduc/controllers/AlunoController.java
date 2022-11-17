package com.api.teaeduc.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.teaeduc.application.AlunoApplication;
import com.api.teaeduc.application.ImagensExerciciosApplication;
import com.api.teaeduc.application.UsuarioApplication;
import com.api.teaeduc.controllers.base.BaseCRUDController;
import com.api.teaeduc.dtos.AlunoDTO;
import com.api.teaeduc.dtos.AlunoFiltroDTO;
import com.api.teaeduc.dtos.FileDTO;
import com.api.teaeduc.dtos.ImagensExerciciosDTO;
import com.api.teaeduc.dtos.ImagensExerciciosFiltroDTO;
import com.api.teaeduc.dtos.UsuarioDTO;
import com.api.teaeduc.models.Aluno;
import com.api.teaeduc.utils.BusinessException;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("${privateApi}/aluno")
public class AlunoController extends BaseCRUDController<AlunoDTO, Aluno, AlunoFiltroDTO> {
    @Autowired
	AlunoApplication alunoApplication;

    @Autowired
	UsuarioApplication usuarioApplication;

    @Autowired
	ImagensExerciciosApplication imagensExerciciosApplication;

    @PostMapping(value="/salvar-aluno")
    public ResponseEntity<Object> saveAluno(@RequestBody AlunoDTO dto){
        try {
            
            AlunoDTO dtoSaved =  this.alunoApplication.salvar(dto);
            return response(dtoSaved);
        } catch (Exception e) {
            return response(e);
        }
    }

    @PutMapping(value="/alterar-aluno")
    public ResponseEntity<Object> alterar(@RequestBody AlunoDTO dto) {
        try {
            Optional<AlunoDTO> aluno = alunoApplication.buscarPorId(dto.getId());

            AlunoDTO alunoAltered = alunoApplication.alterar(dto);

            UsuarioDTO usuario = usuarioApplication.buscarUserPorEmail(aluno.get().getEmail());

            
            usuario.setEmail(dto.getEmail());
            usuario.setTipo("ALUNO");
            usuario.setAtivo(Boolean.TRUE);

            if(dto.getSenha() != null) {
                usuario.setSenha(dto.getSenha());
            } 
            usuarioApplication.alterar(usuario);
            

            return response(alunoAltered);
        } catch (Exception e) {
            return response(e);
        }
    }

    @GetMapping("/aluno-logado")
    public ResponseEntity<Object> usuarioLogado(@RequestParam(value = "email") String email) {
        try {
            AlunoFiltroDTO filtro = new AlunoFiltroDTO();

            if(email != null && !email.equals("")){
                filtro.setEmail(email);
            }

            return response(this.alunoApplication.buscarFiltrado(filtro));
        } catch (Exception e) {
            return response(e);
        }
	}

    @PostMapping("/filtrar-alunos")
    public ResponseEntity<Object> listAlunos(@RequestBody AlunoFiltroDTO filtro) {
        try {
            return response(this.alunoApplication.buscarFiltrado(filtro));
        } catch (Exception e) {
            return response(e);
        }
	}

    @GetMapping("/lista-alunos/{idProfessor}")
    public ResponseEntity<Object> listAlunos(@PathVariable("idProfessor") Long idProfessor) {
        try {
            AlunoFiltroDTO filtro = new AlunoFiltroDTO();

            if(idProfessor != null){
                filtro.setIdProfessor(idProfessor);
            }

            return response(this.alunoApplication.buscarFiltrado(filtro));
        } catch (Exception e) {
            return response(e);
        }
	}

    @PostMapping("/salvar-parabenizacao/{idAluno}")
	public ResponseEntity<Object> salvarAnexoParabenizacao(@PathVariable(value = "idAluno") Long idAluno,
			@RequestParam("file") MultipartFile file) {
		try {
			this.imagensExerciciosApplication.salvarParabenizacaoAluno(file, idAluno);
			return response(ResponseEntity.ok("Imagem salva com sucesso"));
		} catch (Exception e) {
			return response(e);
		}
	}

    @PostMapping("/deletar-imagem")
    public ResponseEntity<Object> deletarAnexo(@RequestParam(value = "id") Long id) throws Exception {
        try{            
            imagensExerciciosApplication.deleteAnexo(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return response(e);
        }
    }

    @PostMapping("/buscar-parabenizacao")
	public ResponseEntity<Object> buscarImagemAluno(@RequestBody ImagensExerciciosFiltroDTO filtro) {
		try {
            List<ImagensExerciciosDTO> dtos = this.imagensExerciciosApplication.buscarFiltrado(filtro);
			return response(dtos.get(0));
		} catch (Exception e) {
			return response(e);
		}
	}


}
