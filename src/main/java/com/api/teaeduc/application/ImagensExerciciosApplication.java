package com.api.teaeduc.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.teaeduc.dtos.ImagensExerciciosDTO;
import com.api.teaeduc.dtos.ImagensExerciciosFiltroDTO;
import com.api.teaeduc.models.ImagensExercicios;
import com.api.teaeduc.services.ImagensExerciciosService;
import com.api.teaeduc.storage.FileSystemStorageService;
import com.api.teaeduc.utils.BusinessException;
import com.api.teaeduc.utils.StringConvertUtil;
import com.api.teaeduc.utils.Util;

@Service
public class ImagensExerciciosApplication extends BaseApplication<ImagensExerciciosDTO, ImagensExercicios, ImagensExerciciosFiltroDTO> {
    
	@Autowired
	ImagensExerciciosService imagensExerciciosService;

	@Autowired 
    public FileSystemStorageService fileSystemStorageService;

    @Override
	public ImagensExerciciosDTO mapToDTO(ImagensExercicios e) {
		ImagensExerciciosDTO dto = new ImagensExerciciosDTO();
		dto.setId(e.getId());
		dto.setIdExercicio(e.getIdExercicio());
		dto.setPathImagem(e.getPathImagem());
        dto.setIdAluno(e.getIdAluno());
		dto.setTipo(e.getTipo());
		return dto;
	}

	@Override
	public ImagensExercicios mapToEntity(ImagensExerciciosDTO dto) {
		ImagensExercicios e = new ImagensExercicios();
		e.setId(dto.getId());
		e.setIdExercicio(dto.getIdExercicio());
		e.setPathImagem(dto.getPathImagem());
        e.setIdAluno(dto.getIdAluno());
		e.setTipo(dto.getTipo());
		return e;
	}


	public List<ImagensExerciciosDTO> buscarFiltrado(ImagensExerciciosFiltroDTO filtro) {
		List<ImagensExercicios> imagens = this.imagensExerciciosService.buscarFiltrado(filtro);
		return imagens.stream().map(this::mapToDTO).collect(Collectors.toList());
	}


	@Override
    @Transactional(rollbackOn = Exception.class)
	public ImagensExerciciosDTO salvar(ImagensExerciciosDTO dto) throws Exception {
        ImagensExercicios e = this.mapToEntity(dto);
        ImagensExerciciosDTO dtoSaved = this.mapToDTO(this.imagensExerciciosService.salvar(e));
		return dtoSaved;
	}

	@Override
    @Transactional(rollbackOn = Exception.class)
    public ImagensExerciciosDTO alterar(ImagensExerciciosDTO dto) throws Exception {
        ImagensExercicios e = this.mapToEntity(dto);
        ImagensExerciciosDTO dtoSaved = this.mapToDTO(this.imagensExerciciosService.alterar(e));
		return dtoSaved;
    }

    @Transactional(rollbackOn = Exception.class)
	public void salvarAnexoExercicio(List<MultipartFile> files, Long idExercicio) throws Exception {
        for(MultipartFile file : files) {
            ImagensExerciciosDTO imagens = new ImagensExerciciosDTO();
            if(idExercicio != null) {
                imagens.setIdExercicio(idExercicio);
                imagens.setTipo("EXERCICIO");
                imagens.setPathImagem(salvarLocal(file, idExercicio, null, "EXERCICIO"));
            }

            salvar(imagens);
        }
	}

    @Transactional(rollbackOn = Exception.class)
	public void salvarAnexoParabenizacao(List<MultipartFile> files, Long idExercicio) throws Exception {
        for(MultipartFile file : files) {
            ImagensExerciciosDTO imagens = new ImagensExerciciosDTO();
            if(idExercicio != null) {
                imagens.setIdExercicio(idExercicio);
                imagens.setTipo("PARABENIZACAO");
                imagens.setPathImagem(salvarLocal(file, idExercicio, null, "PARABENIZACAO"));
            }
            salvar(imagens);
        }
	}

    @Transactional(rollbackOn = Exception.class)
	public void salvarParabenizacaoAluno(MultipartFile file, Long idAluno) throws Exception {
        String relativePath = "";
        List<ImagensExercicios> parabelizacaoAluno = imagensExerciciosService.findByIdAluno(idAluno);

        if(parabelizacaoAluno != null && !parabelizacaoAluno.isEmpty()) {
            imagensExerciciosService.deleteAlunoParabenizacao(parabelizacaoAluno.get(0).getId());
        }

        ImagensExerciciosDTO imagens = new ImagensExerciciosDTO();

        if(idAluno != null) {
            imagens.setIdAluno(idAluno);
            imagens.setTipo("ALUNO_PARABENIZACAO");
            imagens.setPathImagem("temp-path");
            ImagensExerciciosDTO imagemSaved = salvar(imagens);
            relativePath = Util.PATH_SEPARADOR + "aluno_parabenizacao" + Util.PATH_SEPARADOR + imagemSaved.getId() + Util.PATH_SEPARADOR + StringConvertUtil.normlizeString(file.getOriginalFilename());
            this.fileSystemStorageService.store(file, relativePath);
            imagemSaved.setPathImagem(relativePath);
            alterar(imagemSaved);
        }
	}


	public String salvarLocal(MultipartFile file, Long idExercicio, Long idAluno, String tipo) {
        String relativePath = "";
        if(idExercicio != null || idAluno != null) {
            if(tipo.equals("EXERCICIO")) {
                relativePath = Util.PATH_SEPARADOR + "exercicios" + Util.PATH_SEPARADOR + idExercicio + Util.PATH_SEPARADOR + StringConvertUtil.normlizeString(file.getOriginalFilename());
            }
            if(tipo.equals("PARABENIZACAO")) {
                relativePath = Util.PATH_SEPARADOR + "parabenizacao" + Util.PATH_SEPARADOR + idExercicio + Util.PATH_SEPARADOR + StringConvertUtil.normlizeString(file.getOriginalFilename());
            }
            if(tipo.equals("ALUNO_PARABENIZACAO")) {
                relativePath = Util.PATH_SEPARADOR + "aluno_parabenizacao" + Util.PATH_SEPARADOR + idAluno + Util.PATH_SEPARADOR + StringConvertUtil.normlizeString(file.getOriginalFilename());
            }
        }
        this.fileSystemStorageService.store(file, relativePath);
		return relativePath;
	}
	

    public Resource loadAsResource(Long idAnexo) throws BusinessException{
        return this.imagensExerciciosService.loadAsResource(idAnexo);
    }

    public void deleteAnexo(Long idAnexo) throws Exception{
        this.imagensExerciciosService.deleteAnexo(idAnexo);
    }
}
