package com.api.teaeduc.application;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.api.teaeduc.dtos.AlunoDTO;
import com.api.teaeduc.dtos.AlunoFiltroDTO;
import com.api.teaeduc.dtos.ImagensExerciciosDTO;
import com.api.teaeduc.models.Aluno;
import com.api.teaeduc.services.AlunoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoApplication extends BaseApplication<AlunoDTO, Aluno, AlunoFiltroDTO> {
    @Autowired
	AlunoService alunoService;
	@Autowired
	ProfessorApplication professorApplication;
	@Autowired
	AtividadeAlunoApplication atividadeAlunoApplication;
	@Autowired
	ImagensExerciciosApplication imagensExerciciosApplication;

    @Override
	public AlunoDTO mapToDTO(Aluno e) {
		AlunoDTO dto = new AlunoDTO();
		dto.setId(e.getId());
		dto.setCpf(e.getCpf());
		dto.setIdInstituicao(e.getIdInstituicao());
		dto.setIdProfessor(e.getIdProfessor());
		dto.setNome(e.getNome());
		dto.setEmail(e.getEmail());
		if(e.getProfessor() != null) {
			dto.setProfessorDTO(professorApplication.mapToDTO(e.getProfessor()));
		}
		if(e.getAtividadesAluno() != null && !e.getAtividadesAluno().isEmpty()) {
			dto.setAtividadesAluno(e.getAtividadesAluno().stream().map(x -> atividadeAlunoApplication.mapToDTO(x)).collect(Collectors.toList()));
		}
		if(e.getImagensExercicios() != null && !e.getImagensExercicios().isEmpty()) {
			List<ImagensExerciciosDTO> list = e.getImagensExercicios().stream().map(x -> imagensExerciciosApplication.mapToDTO(x)).collect(Collectors.toList());
			
			if(list != null && !list.isEmpty()) {
				dto.setImagensExerciciosDTO(list.get(0));
			}

		}
		return dto;
	}

	@Override
	public Aluno mapToEntity(AlunoDTO dto) {
		Aluno e = new Aluno();
		e.setId(dto.getId());
		e.setIdInstituicao(dto.getIdInstituicao());
		e.setIdProfessor(dto.getIdProfessor());
		e.setNome(dto.getNome());
		e.setEmail(dto.getEmail());
		e.setCpf(dto.getCpf());
		return e;
	}


	public List<AlunoDTO> buscarFiltrado(AlunoFiltroDTO filtro) {
		List<Aluno> professores = this.alunoService.buscarFiltrado(filtro);
		return professores.stream().map(this::mapToDTO).collect(Collectors.toList());
	}


	@Override
    @Transactional(rollbackOn = Exception.class)
	public AlunoDTO salvar(AlunoDTO dto) throws Exception {
        Aluno e = this.mapToEntity(dto);

        AlunoDTO dtoSaved = this.mapToDTO(this.alunoService.salvar(e));

		return dtoSaved;
	}

	@Override
    @Transactional(rollbackOn = Exception.class)
	public AlunoDTO alterar(AlunoDTO dto) throws Exception {
        Aluno e = this.mapToEntity(dto);

        AlunoDTO dtoSaved = this.mapToDTO(this.alunoService.alterar(e));

		return dtoSaved;
	}

	public int countFiltrado(Long idProfessor) {
		AlunoFiltroDTO filtro = new AlunoFiltroDTO();
		filtro.setIdProfessor(idProfessor);
		return this.alunoService.countFiltrado(filtro);
	} 
    
}
