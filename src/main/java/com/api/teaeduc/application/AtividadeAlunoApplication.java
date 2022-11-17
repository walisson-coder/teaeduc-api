package com.api.teaeduc.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.teaeduc.dtos.AtividadeAlunoDTO;
import com.api.teaeduc.dtos.AtividadeAlunoFiltroDTO;
import com.api.teaeduc.models.AtividadeAluno;
import com.api.teaeduc.services.AtividadeAlunoService;
import com.api.teaeduc.utils.BusinessException;

@Service
public class AtividadeAlunoApplication extends BaseApplication<AtividadeAlunoDTO, AtividadeAluno, AtividadeAlunoFiltroDTO> {
    @Autowired
	AtividadeAlunoService atividadeAlunoService;


    @Override
	public AtividadeAlunoDTO mapToDTO(AtividadeAluno e) {
		AtividadeAlunoDTO dto = new AtividadeAlunoDTO();
		dto.setId(e.getId());
		dto.setIdAtividade(e.getIdAtividade());
		dto.setIdAluno(e.getIdAluno());
		dto.setConcluido(e.getConcluido());
		if(e.getAtividade() != null) {
			dto.setNomeAtividade(e.getAtividade().getNomeAtividade());
		}
		return dto;
	}

	@Override
	public AtividadeAluno mapToEntity(AtividadeAlunoDTO dto) {
		AtividadeAluno e = new AtividadeAluno();
		e.setId(dto.getId());
		e.setIdAtividade(dto.getIdAtividade());
		e.setIdAluno(dto.getIdAluno());
		e.setConcluido(dto.getConcluido() == null ? Boolean.FALSE : dto.getConcluido());
		return e;
	}

	public Optional<AtividadeAlunoDTO> findByIdAtividade(Long idAtividade, Long idAluno) {
		Optional<AtividadeAluno> atividadeAlunoOp = this.atividadeAlunoService.findByIdAtividade(idAtividade, idAluno);
		if(atividadeAlunoOp.isPresent()) {
			return Optional.of(mapToDTO(atividadeAlunoOp.get()));
		}
		return Optional.empty();
	}

	public AtividadeAlunoDTO setCloncluido(Long idAtividade, Long idAluno) throws Exception {
		Optional<AtividadeAlunoDTO> atividadeAluno = findByIdAtividade(idAtividade, idAluno);

		if(atividadeAluno.isPresent()) {
			atividadeAluno.get().setConcluido(Boolean.TRUE);
			return alterar(atividadeAluno.get());
		}

		throw new BusinessException("Não foi possível alterar Atividade Aluno.");
	}

	public List<AtividadeAlunoDTO> getAtividadeAlunoByIdAluno(Long idAluno) {
		List<AtividadeAluno> atividadeAlunoList = this.atividadeAlunoService.findAtividadeAlunoByIdAluno(idAluno);
		return atividadeAlunoList.stream().map(x -> mapToDTO(x)).collect(Collectors.toList());
	}

	public List<AtividadeAlunoDTO> getAtividadeFiltro(AtividadeAlunoFiltroDTO atividadeAlunoFiltroDTO) {
		if(atividadeAlunoFiltroDTO.getTipoConcluido().equals("TODOS")) {
			atividadeAlunoFiltroDTO.setConcluido(null);
		}
		if(atividadeAlunoFiltroDTO.getTipoConcluido().equals("NAO_CONCLUIDO")) {
			atividadeAlunoFiltroDTO.setConcluido(Boolean.FALSE);
		}
		if(atividadeAlunoFiltroDTO.getTipoConcluido().equals("CONCLUIDO")) {
			atividadeAlunoFiltroDTO.setConcluido(Boolean.TRUE);
		}

		List<AtividadeAluno> atividadeAlunoList = this.atividadeAlunoService.buscarFiltrado(atividadeAlunoFiltroDTO);
		return atividadeAlunoList.stream().map(x -> mapToDTO(x)).collect(Collectors.toList());
	}
}
