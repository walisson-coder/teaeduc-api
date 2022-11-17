package com.api.teaeduc.application;

import java.util.List;
import java.util.stream.Collectors;

import com.api.teaeduc.dtos.InstituicaoDTO;
import com.api.teaeduc.dtos.InstituicaoFiltroDTO;
import com.api.teaeduc.models.Instituicao;
import com.api.teaeduc.services.InstituicaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstituicaoApplication extends BaseApplication<InstituicaoDTO, Instituicao, InstituicaoFiltroDTO> {
    @Autowired
	InstituicaoService instituicaoService;


    @Override
	public InstituicaoDTO mapToDTO(Instituicao e) {
		InstituicaoDTO dto = new InstituicaoDTO();
		dto.setId(e.getId());
		dto.setNome(e.getNome());
		dto.setCnpj(e.getCnpj());
		dto.setEmail(e.getEmail());
		dto.setCep(e.getCep());
		dto.setEndereco(e.getEndereco());
		dto.setNumero(e.getNumero());
		dto.setBairro(e.getBairro());
		dto.setCidade(e.getCidade());
		dto.setUf(e.getUf());
		dto.setTelefone(e.getTelefone());
		return dto;
	}

	@Override
	public Instituicao mapToEntity(InstituicaoDTO dto) {
		Instituicao e = new Instituicao();
		e.setId(dto.getId());
		e.setNome(dto.getNome());
		e.setCnpj(dto.getCnpj());
		e.setEmail(dto.getEmail());
		e.setCep(dto.getCep());
		e.setEndereco(dto.getEndereco());
		e.setNumero(dto.getNumero());
		e.setBairro(dto.getBairro());
		e.setCidade(dto.getCidade());
		e.setUf(dto.getUf());
		e.setTelefone(dto.getTelefone());
		return e;
	}


	public List<InstituicaoDTO> buscarFiltrado(InstituicaoFiltroDTO filtro) {
		List<Instituicao> instituicoes = this.instituicaoService.buscarFiltrado(filtro);
		return instituicoes.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public List<InstituicaoDTO> buscarInstituicaoByProfessor(InstituicaoFiltroDTO filtro) {
		List<Instituicao> instituicoes = this.instituicaoService.buscarInstituicaoByProfessor(filtro);
		
		return instituicoes.stream().map(this::mapToDTO).collect(Collectors.toList());
	}
    
}
