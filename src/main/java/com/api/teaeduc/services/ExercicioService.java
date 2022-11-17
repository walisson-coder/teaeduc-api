package com.api.teaeduc.services;

import java.util.List;

import com.api.teaeduc.dtos.ExercicioFiltroDTO;
import com.api.teaeduc.models.Exercicio;

public interface ExercicioService extends BaseService<Exercicio, ExercicioFiltroDTO> {
    public List<Exercicio> findByIdAtividade(Long idAtividade);
}