package com.api.teaeduc.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.api.teaeduc.dtos.AtividadeAlunoFiltroDTO;
import com.api.teaeduc.models.AtividadeAluno;
import com.api.teaeduc.repositories.AtividadeAlunoFiltroRepository;
import com.api.teaeduc.repositories.AtividadeAlunoRepository;
import com.api.teaeduc.services.AtividadeAlunoService;

@Service
public class AtividadeAlunoServiceImpl extends BaseServiceImpl<AtividadeAluno, AtividadeAlunoFiltroDTO> implements AtividadeAlunoService {

	@Autowired
	AtividadeAlunoRepository atividadeAlunoRepository;

	@Autowired
	AtividadeAlunoFiltroRepository atividadeAlunoFiltroRepository;

	@Override
	public List<AtividadeAluno> buscarFiltrado(AtividadeAlunoFiltroDTO filtro) {
		return atividadeAlunoFiltroRepository.buscarPaginado(filtro);
	}

	@Override
	public List<AtividadeAluno> buscarFiltrado(PageRequest pageRequest, AtividadeAlunoFiltroDTO filtro) {
		return atividadeAlunoFiltroRepository.buscarPaginado(pageRequest, filtro);
	}

	@Override
	public int countFiltrado(AtividadeAlunoFiltroDTO filtro) {

		return 0;
	}

	@Override
	public Optional<AtividadeAluno> findByIdAtividade(Long idAtividade, Long idAluno) {
		return atividadeAlunoRepository.findByIdAtividadeAndIdAluno(idAtividade, idAluno);
	}

	@Override
	public List<AtividadeAluno> findAtividadeAlunoByIdAluno(Long idAluno) {
		return atividadeAlunoRepository.findByIdAluno(idAluno);
	}
    
}
