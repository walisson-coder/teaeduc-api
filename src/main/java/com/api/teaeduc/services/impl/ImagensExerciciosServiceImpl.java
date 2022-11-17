package com.api.teaeduc.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.api.teaeduc.dtos.ImagensExerciciosFiltroDTO;
import com.api.teaeduc.models.ImagensExercicios;
import com.api.teaeduc.repositories.ImagensExerciciosFiltroRepository;
import com.api.teaeduc.repositories.ImagensExerciciosRepository;
import com.api.teaeduc.services.ImagensExerciciosService;
import com.api.teaeduc.storage.StorageService;
import com.api.teaeduc.utils.BusinessException;

@Service
public class ImagensExerciciosServiceImpl extends BaseServiceImpl<ImagensExercicios, ImagensExerciciosFiltroDTO> implements ImagensExerciciosService {

	@Autowired
	ImagensExerciciosFiltroRepository imagensExerciciosFiltroRepository;

	@Autowired
	ImagensExerciciosRepository imagensExerciciosRepository;

	@Autowired 
    StorageService storageService;

	@Override
	public List<ImagensExercicios> buscarFiltrado(ImagensExerciciosFiltroDTO filtro) {
		return this.imagensExerciciosFiltroRepository.buscarPaginado(filtro);
	}

	@Override
	public List<ImagensExercicios> buscarFiltrado(PageRequest pageRequest, ImagensExerciciosFiltroDTO filtro) {
		return this.imagensExerciciosFiltroRepository.buscarPaginado(pageRequest, filtro);
	}

	@Override
	public int countFiltrado(ImagensExerciciosFiltroDTO filtro) {
		return this.imagensExerciciosFiltroRepository.countFiltrado(filtro);
	}

    public Resource loadAsResource(Long idAnexo) throws BusinessException {
        Optional<ImagensExercicios> anexo = imagensExerciciosRepository.findById(idAnexo);
        if(!anexo.isPresent()){
            throw new BusinessException("Não foi encontrado o arquivo para download");
        }

        return this.storageService.loadAsResource(anexo.get().getPathImagem());
    }

	@Override
	public List<ImagensExercicios> findByIdAluno(Long idAluno) {
		return this.imagensExerciciosRepository.findByIdAluno(idAluno);
	}

    @Override
    public void deleteAnexo(Long id) throws Exception {
        Optional<ImagensExercicios> anexo = imagensExerciciosRepository.findById(id);
        if(anexo.isPresent()){
            storageService.deleteAnexo(anexo.get().getPathImagem());
            deletar(anexo.get());
        }
    }

	@Override
	public void deleteAlunoParabenizacao(Long id) throws Exception {
        Optional<ImagensExercicios> anexo = imagensExerciciosRepository.findById(id);
        if(anexo.isPresent()){
            deletar(anexo.get());
        }
    }
    
}
