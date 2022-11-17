package com.api.teaeduc.repositories.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.api.teaeduc.dtos.AtividadeAlunoFiltroDTO;
import com.api.teaeduc.models.AtividadeAluno;
import com.api.teaeduc.repositories.AtividadeAlunoFiltroRepository;
import com.api.teaeduc.utils.QueryStatement;

@Repository
public class AtividadeAlunoFiltroReposirotyImpl extends QueryStatement<AtividadeAluno> implements AtividadeAlunoFiltroRepository {
    StringBuilder hql;

	@Override
	public int countFiltrado(AtividadeAlunoFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT count(1) ");
		hql.append(" FROM AtividadeAluno atividadeAluno ");
		appendJOINs(filtro);
		appendWhereHQL(filtro, parameters);
		return totalResult(hql, parameters);
	}

	@Override
	public List<AtividadeAluno> buscarPaginado(AtividadeAlunoFiltroDTO filtro) {
		return buscarPaginado(null, filtro);
	}

	@Override
	public List<AtividadeAluno> buscarPaginado(PageRequest pageRequest, AtividadeAlunoFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT atividadeAluno ");
		hql.append(" FROM AtividadeAluno atividadeAluno ");

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
	public StringBuilder appendWhereHQL(AtividadeAlunoFiltroDTO filtro, Map<String, Object> parameters) {
		boolean hasWhere = hql.toString().contains("WHERE");

		if (filtro.getId() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" atividadeAluno.id = :id ");
			parameters.put("id", filtro.getId());
		}

		if (filtro.getNomeAtividade() != null && !filtro.getNomeAtividade().equals("")) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" atividadeAluno.atividade.nomeAtividade LIKE :nomeAtividade ");
			parameters.put("nomeAtividade", "%" + filtro.getNomeAtividade() + "%");
		}

		if (filtro.getConcluido() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" atividadeAluno.concluido = :concluido ");
			parameters.put("concluido", filtro.getConcluido());
		}

		if (filtro.getIdAluno() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" atividadeAluno.idAluno = :idAluno ");
			parameters.put("idAluno", filtro.getIdAluno());
		}

		return hql;
	}

	@Override
	public StringBuilder appendOrderByHQL(AtividadeAlunoFiltroDTO filtro, Map<String, Object> parameters) {
		hql.append(" ORDER BY atividadeAluno.id ");
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

	@Override
	public StringBuilder appendJOINs(AtividadeAlunoFiltroDTO filtro) {
		// TODO Auto-generated method stub
		return null;
	}
}
