package com.api.teaeduc.services.impl;

import java.util.List;

import com.api.teaeduc.dtos.ProfessorFiltroDTO;
import com.api.teaeduc.models.Professor;
import com.api.teaeduc.repositories.ProfessorFiltroRepository;
import com.api.teaeduc.services.ProfessorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProfessorServiceImpl extends BaseServiceImpl<Professor, ProfessorFiltroDTO> implements ProfessorService {

	@Autowired
	ProfessorFiltroRepository professorFiltroRepository;

	@Override
	public List<Professor> buscarFiltrado(ProfessorFiltroDTO filtro) {
		return this.professorFiltroRepository.buscarPaginado(filtro);
	}

	@Override
	public List<Professor> buscarFiltrado(PageRequest pageRequest, ProfessorFiltroDTO filtro) {
		return this.professorFiltroRepository.buscarPaginado(pageRequest, filtro);
	}

	@Override
	public int countFiltrado(ProfessorFiltroDTO filtro) {
		return this.professorFiltroRepository.countFiltrado(filtro);
	}

       
    
}
