package com.api.teaeduc.services;

import java.util.List;

import org.springframework.core.io.Resource;

import com.api.teaeduc.dtos.ImagensExerciciosFiltroDTO;
import com.api.teaeduc.models.ImagensExercicios;
import com.api.teaeduc.utils.BusinessException;

public interface ImagensExerciciosService extends BaseService<ImagensExercicios, ImagensExerciciosFiltroDTO> {
    public Resource loadAsResource(Long idAnexo) throws BusinessException;
    public void deleteAnexo(Long id) throws Exception;
    public List<ImagensExercicios> findByIdAluno(Long idAluno);
    public void deleteAlunoParabenizacao(Long id) throws Exception;
}
