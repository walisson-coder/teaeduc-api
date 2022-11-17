package com.api.teaeduc.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.api.teaeduc.dtos.ExercicioFiltroDTO;
import com.api.teaeduc.models.Exercicio;
import com.api.teaeduc.repositories.ExercicioFiltroRepository;
import com.api.teaeduc.repositories.ExercicioRepository;
import com.api.teaeduc.services.ExercicioService;

@Service
public class ExercicioServiceImpl extends BaseServiceImpl<Exercicio, ExercicioFiltroDTO> implements ExercicioService {

	@Autowired
	ExercicioFiltroRepository exercicioFiltroRepository;

	@Autowired
	ExercicioRepository exercicioRepository;

	@Override
	public List<Exercicio> buscarFiltrado(ExercicioFiltroDTO filtro) {
		return this.exercicioFiltroRepository.buscarPaginado(filtro);
	}

	@Override
	public List<Exercicio> buscarFiltrado(PageRequest pageRequest, ExercicioFiltroDTO filtro) {
		return this.exercicioFiltroRepository.buscarPaginado(pageRequest, filtro);
	}

	@Override
	public int countFiltrado(ExercicioFiltroDTO filtro) {
		return this.exercicioFiltroRepository.countFiltrado(filtro);
	}
    
	@Override
	public List<Exercicio> findByIdAtividade(Long idAtividade) {
		return this.exercicioRepository.findByIdAtividade(idAtividade);
	}
}
