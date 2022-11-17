package com.api.teaeduc.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.api.teaeduc.dtos.AtividadeFiltroDTO;
import com.api.teaeduc.models.Atividade;
import com.api.teaeduc.models.Exercicio;
import com.api.teaeduc.models.ImagensExercicios;
import com.api.teaeduc.repositories.AtividadeFiltroRepository;
import com.api.teaeduc.services.AtividadeService;
import com.api.teaeduc.services.ExercicioService;
import com.api.teaeduc.services.ImagensExerciciosService;

@Service
public class AtividadeServiceImpl extends BaseServiceImpl<Atividade, AtividadeFiltroDTO> implements AtividadeService {

	@Autowired
	AtividadeFiltroRepository atividadeFiltroRepository;

	@Autowired
	ExercicioService exercicioService;

	@Autowired
	ImagensExerciciosService imagensExerciciosService;

	@Override
	public List<Atividade> buscarFiltrado(AtividadeFiltroDTO filtro) {
		return this.atividadeFiltroRepository.buscarPaginado(filtro);
	}

	@Override
	public List<Atividade> buscarFiltrado(PageRequest pageRequest, AtividadeFiltroDTO filtro) {
		return this.atividadeFiltroRepository.buscarPaginado(pageRequest, filtro);
	}

	@Override
	public int countFiltrado(AtividadeFiltroDTO filtro) {
		return this.atividadeFiltroRepository.countFiltrado(filtro);
	}

	@Override
	public Atividade salvar(Atividade atividade) throws Exception {
		if(atividade.getId() != null) {
			this.validaExercicioToSave(atividade);
		}
		Atividade atividadeSalva = super.salvar(atividade);

		atividade.getExercicios().forEach(x -> x.setIdAtividade(atividadeSalva.getId()));

		List<Exercicio> exerciciosSalvos = exercicioService.salvarLista(atividade.getExercicios());

		if(exerciciosSalvos != null && !exerciciosSalvos.isEmpty()) {
			atividadeSalva.setExercicios(exerciciosSalvos);
		}

		return atividadeSalva;
	}


	private void validaExercicioToSave(Atividade atividade) throws Exception {
		List<Exercicio> exercicios = this.exercicioService.findByIdAtividade(atividade.getId());
		if(exercicios != null && !exercicios.isEmpty()) {
			List<ImagensExercicios> imagens = exercicios.stream().map(Exercicio::getImagensExercicio).flatMap(Collection::stream).collect(Collectors.toList());
			
			for(Exercicio exercicio : exercicios) {
				if(atividade.getExercicios().stream().noneMatch(x -> x.getId() != null && x.getId().equals(exercicio.getId()))) {
					
					for(ImagensExercicios imagem : imagens) {
						if(imagem.getIdExercicio().equals(exercicio.getId())) {
							this.imagensExerciciosService.deleteAlunoParabenizacao(imagem.getId());
						}
					}

					this.exercicioService.deletar(exercicio);

				}	
			}
		}
	}

	@Override
	public void deletar(Atividade obj) throws Exception {
		List<Exercicio> exercicios = this.exercicioService.findByIdAtividade(obj.getId());

		List<ImagensExercicios> imagens = exercicios.stream().map(Exercicio::getImagensExercicio).flatMap(Collection::stream).collect(Collectors.toList());

		for(ImagensExercicios imagem : imagens) {
			this.imagensExerciciosService.deleteAnexo(imagem.getId());
		}

		for(Exercicio exercicio : exercicios) {
			this.exercicioService.deletar(exercicio);
		}

		super.deletar(obj);
	}


	
    
}
