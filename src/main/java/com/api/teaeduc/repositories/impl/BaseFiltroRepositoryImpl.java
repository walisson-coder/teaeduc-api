package com.api.teaeduc.repositories.impl;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.api.teaeduc.dtos.FiltroDTO;
import com.api.teaeduc.models.BaseEntity;
import com.api.teaeduc.repositories.BaseFiltroRepository;
import com.api.teaeduc.utils.QueryStatement;

import org.springframework.data.domain.PageRequest;



public abstract class BaseFiltroRepositoryImpl<E extends BaseEntity, F extends FiltroDTO> extends QueryStatement<E> implements BaseFiltroRepository<E,F> {
	
	private StringBuilder hql;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Class getGenericClass() {
		return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	protected String getGenericClassName() {
		return getGenericClass().getSimpleName();
	}

	protected StringBuilder getSimpleHQL() {
		String entity = getGenericClassName();
		String entityAlias = entity.substring(0, 1).toLowerCase() + entity.substring(1);
		
		hql = new StringBuilder();
		hql.append("SELECT " + entityAlias + " ");
		hql.append("FROM " + entity + " " + entityAlias + " ");
		
		return hql;
	}

		
	protected StringBuilder getSimpleHQLCount() {
		String entity = getGenericClassName();
		String entityAlias = entity.substring(0, 1).toLowerCase() + entity.substring(1);
		
		hql = new StringBuilder();
		hql.append("SELECT COUNT(1) ");
		hql.append("FROM " + entity + " " + entityAlias + " ");
		
		return hql;
	}

	protected static Boolean appendWhereOrAnd(StringBuilder hql, boolean hasWhere) {
		if (hasWhere) {
			hql.append(" AND ");
		} else {
			hql.append(" WHERE ");
			hasWhere = true;
		}
		return hasWhere;
	}

	
	@Override
	public int countFiltrado(F filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		getSimpleHQLCount();
		appendJOINs(filtro);
		appendWhereHQL(filtro, parameters);
		return totalResult(hql, parameters);
	}

	@Override
	public List<E> buscarPaginado(F filtro) {
		return buscarPaginado(null, filtro);
	}

	@Override
	public List<E> buscarPaginado(PageRequest pageRequest, F filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		getSimpleHQL();
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
	public StringBuilder appendJOINs(F filtro) {
		return hql;
	}

	@Override
	public StringBuilder appendWhereHQL(F filtro, Map<String, Object> parameters) {
		return hql;
	}

	@Override
	public StringBuilder appendOrderByHQL(F filtro, Map<String, Object> parameters) {
		return hql;
	}

}
