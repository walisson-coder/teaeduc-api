package com.api.teaeduc.services.impl;

import java.util.List;

import com.api.teaeduc.dtos.InstituicaoFiltroDTO;
import com.api.teaeduc.models.Instituicao;
import com.api.teaeduc.repositories.InstituicaoFiltroRepository;
import com.api.teaeduc.services.InstituicaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class InstituicaoServiceImpl extends BaseServiceImpl<Instituicao, InstituicaoFiltroDTO> implements InstituicaoService {

	@Autowired
	InstituicaoFiltroRepository instituicaoFiltroRepository;

	@Override
	public List<Instituicao> buscarFiltrado(InstituicaoFiltroDTO filtro) {
		return this.instituicaoFiltroRepository.buscarPaginado(filtro);
	}

	@Override
	public List<Instituicao> buscarFiltrado(PageRequest pageRequest, InstituicaoFiltroDTO filtro) {
		return this.instituicaoFiltroRepository.buscarPaginado(pageRequest, filtro);
	}

	@Override
	public int countFiltrado(InstituicaoFiltroDTO filtro) {
		return this.instituicaoFiltroRepository.countFiltrado(filtro);
	}

	@Override
	public List<Instituicao> buscarInstituicaoByProfessor(InstituicaoFiltroDTO filtro) {
		return this.instituicaoFiltroRepository.buscarInstituicaoByProfessor(filtro);
	}

       
    
}
