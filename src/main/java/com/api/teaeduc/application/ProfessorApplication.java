package com.api.teaeduc.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.api.teaeduc.dtos.ProfessorDTO;
import com.api.teaeduc.dtos.ProfessorFiltroDTO;
import com.api.teaeduc.models.Professor;
import com.api.teaeduc.services.ProfessorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorApplication extends BaseApplication<ProfessorDTO, Professor, ProfessorFiltroDTO> {
    @Autowired
	ProfessorService professorService;


    @Override
	public ProfessorDTO mapToDTO(Professor e) {
		ProfessorDTO dto = new ProfessorDTO();
		dto.setId(e.getId());
		dto.setNome(e.getNome());
		dto.setCpf(e.getCpf());
		dto.setEmail(e.getEmail());
		dto.setTelefone(e.getTelefone());
		dto.setIdInstituicao(e.getIdInstituicao());
		
		return dto;
	}

	@Override
	public Professor mapToEntity(ProfessorDTO dto) {
		Professor e = new Professor();
		e.setId(dto.getId());
		e.setNome(dto.getNome());
		e.setCpf(dto.getCpf());
		e.setEmail(dto.getEmail());
		e.setTelefone(dto.getTelefone());
		e.setIdInstituicao(dto.getIdInstituicao());
		return e;
	}


	public List<ProfessorDTO> buscarFiltrado(ProfessorFiltroDTO filtro) {
		List<Professor> professores = this.professorService.buscarFiltrado(filtro);
		return professores.stream().map(this::mapToDTO).collect(Collectors.toList());
	}


	@Override
    @Transactional(rollbackOn = Exception.class)
	public ProfessorDTO salvar(ProfessorDTO dto) throws Exception {
        Professor e = this.mapToEntity(dto);
        ProfessorDTO dtoSaved = this.mapToDTO(this.professorService.salvar(e));
		return dtoSaved;
	}

	public int countFiltrado(Long idInstituicao) {
		ProfessorFiltroDTO filtro = new ProfessorFiltroDTO();
		filtro.setIdInstituicao(idInstituicao);
		return this.professorService.countFiltrado(filtro);
	} 
    
}
