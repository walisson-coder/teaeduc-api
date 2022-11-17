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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.teaeduc.application.AtividadeAlunoApplication;
import com.api.teaeduc.application.AtividadeApplication;
import com.api.teaeduc.application.ExercicioApplication;
import com.api.teaeduc.application.ImagensExerciciosApplication;
import com.api.teaeduc.controllers.base.BaseCRUDController;
import com.api.teaeduc.dtos.AtividadeAlunoDTO;
import com.api.teaeduc.dtos.AtividadeAlunoFiltroDTO;
import com.api.teaeduc.dtos.AtividadeDTO;
import com.api.teaeduc.dtos.AtividadeFiltroDTO;
import com.api.teaeduc.dtos.FileDTO;
import com.api.teaeduc.models.Atividade;
import com.api.teaeduc.utils.BusinessException;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("${privateApi}/atividade")
public class AtividadeController extends BaseCRUDController<AtividadeDTO, Atividade, AtividadeFiltroDTO> {
    @Autowired
	AtividadeApplication atividadeApplication;
    @Autowired
	ImagensExerciciosApplication imagensExerciciosApplication;
    @Autowired
	AtividadeAlunoApplication atividadeAlunoApplication;

    @PostMapping("/buscar")
	public ResponseEntity<Object> salvarAnexoImagem(@RequestBody AtividadeFiltroDTO filtro) {
		try {
			return response(this.atividadeApplication.buscarFiltrado(filtro));
		} catch (Exception e) {
			return response(e);
		}
	}
    
    @PostMapping("/salvar-imagem/{idExercicio}")
	public ResponseEntity<Object> salvarAnexoImagem(@PathVariable(value = "idExercicio") Long idExercicio,
			@RequestParam("file") List<MultipartFile> files) {
		try {
			this.imagensExerciciosApplication.salvarAnexoExercicio(files, idExercicio);
			return response(ResponseEntity.ok("Imagem salva com sucesso"));
		} catch (Exception e) {
			return response(e);
		}
	}

    @PostMapping("/salvar-parabenizacao/{idExercicio}")
	public ResponseEntity<Object> salvarAnexoParabenizacao(@PathVariable(value = "idExercicio") Long idExercicio,
			@RequestParam("file") List<MultipartFile> files) {
		try {
			this.imagensExerciciosApplication.salvarAnexoParabenizacao(files, idExercicio);
			return response(ResponseEntity.ok("Imagem salva com sucesso"));
		} catch (Exception e) {
			return response(e);
		}
	}

    // @GetMapping(value = "buscar-imagem/{idAnexo}", produces = MediaType.ALL_VALUE)
    @RequestMapping(value = "buscar-imagem/{idAnexo}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> download(@PathVariable(value = "idAnexo") Long idAnexo) throws BusinessException, IOException {
        Resource resource = imagensExerciciosApplication.loadAsResource(idAnexo);
        InputStream in = resource.getInputStream();
        FileDTO dto = new FileDTO();
        dto.setBytes(IOUtils.toByteArray(in));
        dto.setFileName(resource.getFilename());
        dto.setMimeType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return response(dto);
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

	@DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") Long id) {
        try {
            Optional<AtividadeDTO> dto = atividadeApplication.buscarPorId(id);
            if (!dto.isPresent()) {
                return response(new BusinessException("Registro não encontrado"));
            }
            atividadeApplication.deletar(dto.get());
            return response(dto.get());
        } catch (Exception e) {
            return response(e);
        }
    }


    @PostMapping("/atribuir-atividade")
	public ResponseEntity<Object> atribuirAtividade(@RequestBody AtividadeAlunoDTO atividadeAlunoDTO) {
		try {
			this.atividadeAlunoApplication.salvar(atividadeAlunoDTO);
			return response(ResponseEntity.ok("Imagem salva com sucesso"));
		} catch (Exception e) {
			return response(e);
		}
	}

    @DeleteMapping("/remove-atividade/{id}")
    public ResponseEntity<Object> removerAtividadeAluno(@PathVariable("id") Long id) {
        try {
            Optional<AtividadeAlunoDTO> dto = atividadeAlunoApplication.buscarPorId(id);
            if (!dto.isPresent()) {
                return response(new BusinessException("Registro não encontrado"));
            }
            atividadeAlunoApplication.deletar(dto.get());
            return response(dto.get());
        } catch (Exception e) {
            return response(e);
        }
    }

    @PostMapping("/set-concluido")
	public ResponseEntity<Object> salvarAnexoImagem(@RequestBody AtividadeAlunoDTO atividadeAlunoDTO) {
		try {
			AtividadeAlunoDTO setCloncluido = this.atividadeAlunoApplication.setCloncluido(atividadeAlunoDTO.getIdAtividade(), atividadeAlunoDTO.getIdAluno());
			return response(setCloncluido);
		} catch (Exception e) {
			return response(e);
		}
	}


    @GetMapping("/buscar-idaluno/{id}")
    public ResponseEntity<Object> getAtividadeAlunoByIdAluno(@PathVariable("id") Long id) {
        try {
            List<AtividadeAlunoDTO> dto = atividadeAlunoApplication.getAtividadeAlunoByIdAluno(id);
            return response(dto);
        } catch (Exception e) {
            return response(e);
        }
    }

    @PostMapping("/filtrar")
    public ResponseEntity<Object> getAtividadeAlunoByIdAluno(@RequestBody AtividadeAlunoFiltroDTO atividadeAlunoFiltroDTO) {
        try {
            List<AtividadeAlunoDTO> dto = atividadeAlunoApplication.getAtividadeFiltro(atividadeAlunoFiltroDTO);
            return response(dto);
        } catch (Exception e) {
            return response(e);
        }
    }
}
