package com.api.teaeduc.repositories.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.api.teaeduc.dtos.ProfessorFiltroDTO;
import com.api.teaeduc.models.Professor;
import com.api.teaeduc.repositories.ProfessorFiltroRepository;
import com.api.teaeduc.utils.QueryStatement;

@Repository
public class ProfessorFiltroReposirotyImpl extends QueryStatement<Professor> implements ProfessorFiltroRepository {
    StringBuilder hql;

	@Override
	public int countFiltrado(ProfessorFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT count(1) ");
		hql.append(" FROM Professor professor ");
		appendJOINs(filtro);
		appendWhereHQL(filtro, parameters);
		return totalResult(hql, parameters);
	}

	@Override
	public List<Professor> buscarPaginado(ProfessorFiltroDTO filtro) {
		return buscarPaginado(null, filtro);
	}

	@Override
	public List<Professor> buscarPaginado(PageRequest pageRequest, ProfessorFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT professor ");
		hql.append(" FROM Professor professor ");

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
	public StringBuilder appendJOINs(ProfessorFiltroDTO filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder appendWhereHQL(ProfessorFiltroDTO filtro, Map<String, Object> parameters) {
		boolean hasWhere = hql.toString().contains("WHERE");

		if (filtro.getIdInstituicao() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" professor.idInstituicao = :idInstituicao ");
			parameters.put("idInstituicao", filtro.getIdInstituicao());
		}
		if (filtro.getEmail() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" professor.email = :email ");
			parameters.put("email", filtro.getEmail());
		}
		if (filtro.getNome() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" UPPER(professor.nome) LIKE :nome ");
			parameters.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
		return hql;
	}

	@Override
	public StringBuilder appendOrderByHQL(ProfessorFiltroDTO filtro, Map<String, Object> parameters) {
		hql.append(" ORDER BY professor.nome ");
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
