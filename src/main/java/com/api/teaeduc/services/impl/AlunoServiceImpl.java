package com.api.teaeduc.services.impl;

import java.util.List;

import com.api.teaeduc.dtos.AlunoFiltroDTO;
import com.api.teaeduc.models.Aluno;
import com.api.teaeduc.repositories.AlunoFiltroRepository;
import com.api.teaeduc.services.AlunoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AlunoServiceImpl extends BaseServiceImpl<Aluno, AlunoFiltroDTO> implements AlunoService {

	@Autowired
	AlunoFiltroRepository alunoFiltroRepository ;

	@Override
	public List<Aluno> buscarFiltrado(AlunoFiltroDTO filtro) {
		return this.alunoFiltroRepository.buscarPaginado(filtro);
	}

	@Override
	public List<Aluno> buscarFiltrado(PageRequest pageRequest, AlunoFiltroDTO filtro) {
		return this.alunoFiltroRepository.buscarPaginado(pageRequest, filtro);
	}

	@Override
	public int countFiltrado(AlunoFiltroDTO filtro) {
		return this.alunoFiltroRepository.countFiltrado(filtro);
	}

       
    
}
