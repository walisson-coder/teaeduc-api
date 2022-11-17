package com.api.teaeduc.repositories.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.api.teaeduc.dtos.ImagensExerciciosFiltroDTO;
import com.api.teaeduc.models.ImagensExercicios;
import com.api.teaeduc.repositories.ImagensExerciciosFiltroRepository;
import com.api.teaeduc.utils.QueryStatement;

@Repository
public class ImagensExerciciosFiltroReposirotyImpl extends QueryStatement<ImagensExercicios> implements ImagensExerciciosFiltroRepository {
    StringBuilder hql;

	@Override
	public int countFiltrado(ImagensExerciciosFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT count(1) ");
		hql.append(" FROM ImagensExercicios imagens ");
		appendJOINs(filtro);
		appendWhereHQL(filtro, parameters);
		return totalResult(hql, parameters);
	}

	@Override
	public List<ImagensExercicios> buscarPaginado(ImagensExerciciosFiltroDTO filtro) {
		return buscarPaginado(null, filtro);
	}

	@Override
	public List<ImagensExercicios> buscarPaginado(PageRequest pageRequest, ImagensExerciciosFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT imagens ");
		hql.append(" FROM ImagensExercicios imagens ");

		appendJOINs(filtro);
		appendWhereHQL(filtro, parameters);
		appendOrderByHQL(filtro, parameters);

		Integer pageNumber = null;
		Integer pageSize = null;

		if (pageRequest != null) {
			pageNumber = pageRequest.getPageNumber();
			pageSize = pageRequest.getPageSize();
		}

		return asList(hql, parameters, pageNumber, pageSize);
	}

	@Override
	public StringBuilder appendJOINs(ImagensExerciciosFiltroDTO filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder appendWhereHQL(ImagensExerciciosFiltroDTO filtro, Map<String, Object> parameters) {
		boolean hasWhere = hql.toString().contains("WHERE");

		if (filtro.getId() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" imagens.id = :id ");
			parameters.put("id", filtro.getId());
		}
		
		return hql;
	}

	@Override
	public StringBuilder appendOrderByHQL(ImagensExerciciosFiltroDTO filtro, Map<String, Object> parameters) {
		hql.append(" ORDER BY imagens.pathImagem ");
		return hql;
	}

	private static Boolean appendWhereOrAnd(StringBuilder hql, boolean hasWhere) {
		if (hasWhere) {
			hql.append(" AND ");
		} else {
			hql.append(" WHERE ");
			hasWhere = true;
		}
		return hasWhere;
	}
}
