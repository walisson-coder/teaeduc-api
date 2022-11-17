package com.api.teaeduc.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.teaeduc.dtos.AtividadeDTO;
import com.api.teaeduc.dtos.AtividadeFiltroDTO;
import com.api.teaeduc.models.Atividade;
import com.api.teaeduc.services.AtividadeService;
import com.api.teaeduc.services.ExercicioService;

@Service
public class AtividadeApplication extends BaseApplication<AtividadeDTO, Atividade, AtividadeFiltroDTO> {
    @Autowired
	AtividadeService atividadeService;

	@Autowired
	ExercicioApplication exercicioApplication;

	@Autowired
	ExercicioService exercicioService;


    @Override
	public AtividadeDTO mapToDTO(Atividade e) {
		AtividadeDTO dto = new AtividadeDTO();
		dto.setId(e.getId());
		dto.setIdProfessor(e.getIdProfessor());
		dto.setNomeAtividade(e.getNomeAtividade());
		dto.setQtdAtividade(e.getQtdAtividade());
		dto.setTipoAtividade(e.getTipoAtividade());
		if(e.getExercicios() != null && !e.getExercicios().isEmpty()) {
			dto.setExercicios(e.getExercicios().stream().map(x -> exercicioApplication.mapToDTO(x)).collect(Collectors.toList()));
		}
		return dto;
	}

	@Override
	public Atividade mapToEntity(AtividadeDTO dto) {
		Atividade e = new Atividade();
		e.setId(dto.getId());
		e.setIdProfessor(dto.getIdProfessor());
		e.setNomeAtividade(dto.getNomeAtividade());
		e.setQtdAtividade(dto.getQtdAtividade());
		e.setTipoAtividade(dto.getTipoAtividade());
		if(dto.getExercicios() != null && !dto.getExercicios().isEmpty()) {
			e.setExercicios(dto.getExercicios().stream().map(x -> exercicioApplication.mapToEntity(x)).collect(Collectors.toList()));
		}
		return e;
	}


	public List<AtividadeDTO> buscarFiltrado(AtividadeFiltroDTO filtro) {
		List<Atividade> professores = this.atividadeService.buscarFiltrado(filtro);
		return professores.stream().map(this::mapToDTO).collect(Collectors.toList());
	}


	@Override
    @Transactional(rollbackOn = Exception.class)
	public AtividadeDTO salvar(AtividadeDTO dto) throws Exception {
        Atividade e = this.mapToEntity(dto);
        AtividadeDTO dtoSaved = this.mapToDTO(this.atividadeService.salvar(e));
		return dtoSaved;
	}

	

	@Override
	public AtividadeDTO alterar(AtividadeDTO dto) throws Exception {
		return super.salvar(dto);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deletar(AtividadeDTO dto) throws Exception {
		super.deletar(dto);
	}

	
    
}
