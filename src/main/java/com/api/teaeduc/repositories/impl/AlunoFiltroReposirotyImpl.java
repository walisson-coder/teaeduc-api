package com.api.teaeduc.repositories.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.api.teaeduc.dtos.AlunoFiltroDTO;
import com.api.teaeduc.models.Aluno;
import com.api.teaeduc.repositories.AlunoFiltroRepository;
import com.api.teaeduc.utils.QueryStatement;

@Repository
public class AlunoFiltroReposirotyImpl extends QueryStatement<Aluno> implements AlunoFiltroRepository {
    StringBuilder hql;

	@Override
	public int countFiltrado(AlunoFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT count(1) ");
		hql.append(" FROM Aluno aluno ");
		appendJOINs(filtro);
		appendWhereHQL(filtro, parameters);
		return totalResult(hql, parameters);
	}

	@Override
	public List<Aluno> buscarPaginado(AlunoFiltroDTO filtro) {
		return buscarPaginado(null, filtro);
	}

	@Override
	public List<Aluno> buscarPaginado(PageRequest pageRequest, AlunoFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT aluno ");
		hql.append(" FROM Aluno aluno ");

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
	public StringBuilder appendJOINs(AlunoFiltroDTO filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder appendWhereHQL(AlunoFiltroDTO filtro, Map<String, Object> parameters) {
		boolean hasWhere = hql.toString().contains("WHERE");
		if (filtro.getEmail() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" aluno.email = :email ");
			parameters.put("email", filtro.getEmail());
		}
		if (filtro.getNome() != null && !filtro.getNome().equals("")) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" UPPER(aluno.nome) LIKE :nome ");
			parameters.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		if (filtro.getIdProfessor() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" aluno.idProfessor = :idProfessor ");
			parameters.put("idProfessor", filtro.getIdProfessor());
		}
		
		return hql;
	}

	@Override
	public StringBuilder appendOrderByHQL(AlunoFiltroDTO filtro, Map<String, Object> parameters) {
		hql.append(" ORDER BY aluno.nome ");
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
