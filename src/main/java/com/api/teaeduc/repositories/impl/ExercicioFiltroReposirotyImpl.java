package com.api.teaeduc.repositories.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.api.teaeduc.dtos.ExercicioFiltroDTO;
import com.api.teaeduc.models.Exercicio;
import com.api.teaeduc.repositories.ExercicioFiltroRepository;
import com.api.teaeduc.utils.QueryStatement;

@Repository
public class ExercicioFiltroReposirotyImpl extends QueryStatement<Exercicio> implements ExercicioFiltroRepository {
    StringBuilder hql;

	@Override
	public int countFiltrado(ExercicioFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT count(1) ");
		hql.append(" FROM Exercicio erxercicio ");
		appendJOINs(filtro);
		appendWhereHQL(filtro, parameters);
		return totalResult(hql, parameters);
	}

	@Override
	public List<Exercicio> buscarPaginado(ExercicioFiltroDTO filtro) {
		return buscarPaginado(null, filtro);
	}

	@Override
	public List<Exercicio> buscarPaginado(PageRequest pageRequest, ExercicioFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT exercicio ");
		hql.append(" FROM Exercicio exercicio ");

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
	public StringBuilder appendJOINs(ExercicioFiltroDTO filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder appendWhereHQL(ExercicioFiltroDTO filtro, Map<String, Object> parameters) {
		boolean hasWhere = hql.toString().contains("WHERE");

		if (filtro.getId() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" exercicio.id = :id ");
			parameters.put("id", filtro.getId());
		}
		
		return hql;
	}

	@Override
	public StringBuilder appendOrderByHQL(ExercicioFiltroDTO filtro, Map<String, Object> parameters) {
		hql.append(" ORDER BY exercicio.nome ");
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
