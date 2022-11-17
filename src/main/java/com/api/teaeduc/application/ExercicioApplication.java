package com.api.teaeduc.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.api.teaeduc.dtos.ExercicioDTO;
import com.api.teaeduc.dtos.ExercicioFiltroDTO;
import com.api.teaeduc.dtos.ImagensExerciciosDTO;
import com.api.teaeduc.models.Exercicio;
import com.api.teaeduc.models.enumeration.TipoAtividadeEnum;
import com.api.teaeduc.services.ExercicioService;
import com.api.teaeduc.utils.BusinessException;
import com.api.teaeduc.utils.Fragmentador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExercicioApplication extends BaseApplication<ExercicioDTO, Exercicio, ExercicioFiltroDTO> {
    
	@Autowired
	ExercicioService professorService;

	@Autowired
	ImagensExerciciosApplication imagensExerciciosApplication;

    @Override
	public ExercicioDTO mapToDTO(Exercicio e) {
		ExercicioDTO dto = new ExercicioDTO();
		dto.setId(e.getId());
		dto.setIdAtividade(e.getIdAtividade());
		dto.setPalavra(e.getPalavra());
		try {
			if(e.getAtividade() != null && e.getAtividade().getTipoAtividade().equals(TipoAtividadeEnum.SILABAS)) {
				dto.setPalavraList(this.separarPorSilaba(e.getPalavra()));
			}
			if(e.getAtividade() != null && e.getAtividade().getTipoAtividade().equals(TipoAtividadeEnum.LETRAS)) {
				dto.setPalavraList(this.separarPorLetra(e.getPalavra()));
			}
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}
		dto.setTipoExercicio(e.getTipoExercicio());
		if(e.getImagensExercicio() != null && !e.getImagensExercicio().isEmpty()) {
			dto.setImagensExercicio(e.getImagensExercicio().stream().map(x -> imagensExerciciosApplication.mapToDTO(x)).collect(Collectors.toList()));
			if(dto.getImagensExercicio() != null && !dto.getImagensExercicio().isEmpty()) {
				for(ImagensExerciciosDTO dtoImagem : dto.getImagensExercicio()) {
					if(dtoImagem.getTipo().equals("EXERCICIO")) {
						String[] splitImagem = dtoImagem.getPathImagem().split("/");
						dto.setNomeImagem(splitImagem[splitImagem.length - 1]);
					}
					if(dtoImagem.getTipo().equals("PARABENIZACAO")) {
						String[] splitImagem = dtoImagem.getPathImagem().split("/");
						dto.setNomeParabenizacao(splitImagem[splitImagem.length - 1]);
					}
				}
			}
		}
		return dto;
	}

	@Override
	public Exercicio mapToEntity(ExercicioDTO dto) {
		Exercicio e = new Exercicio();
		e.setId(dto.getId());
		e.setIdAtividade(dto.getIdAtividade());
		e.setPalavra(dto.getPalavra());
		e.setTipoExercicio(dto.getTipoExercicio());
		return e;
	}


	public List<ExercicioDTO> buscarFiltrado(ExercicioFiltroDTO filtro) {
		List<Exercicio> professores = this.professorService.buscarFiltrado(filtro);
		return professores.stream().map(this::mapToDTO).collect(Collectors.toList());
	}


	@Override
    @Transactional(rollbackOn = Exception.class)
	public ExercicioDTO salvar(ExercicioDTO dto) throws Exception {
        Exercicio e = this.mapToEntity(dto);
        ExercicioDTO dtoSaved = this.mapToDTO(this.professorService.salvar(e));
		return dtoSaved;
	}

	public List<String> separarPorSilaba(String palavra) throws BusinessException {
		if(palavra != null && !palavra.isEmpty()) {
			Fragmentador fragmentador = new Fragmentador();
			return fragmentador.contarSilabas(palavra).stream().map(x -> x.toUpperCase()).collect(Collectors.toList());
		}
		else {
			throw new BusinessException("Erro ao fragmentar palavra: " + palavra);
		} 
	}

	public List<String> separarPorLetra(String palavra) throws BusinessException {
		if(palavra != null && !palavra.isEmpty()) {
			return Arrays.asList(palavra.split("(?<=\\G.{1})"));
		}
		else {
			throw new BusinessException("Erro ao fragmentar palavra: " + palavra);
		} 
	}
}
