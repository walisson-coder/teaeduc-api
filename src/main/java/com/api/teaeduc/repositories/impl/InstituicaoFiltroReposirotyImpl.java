package com.api.teaeduc.repositories.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.api.teaeduc.dtos.InstituicaoFiltroDTO;
import com.api.teaeduc.models.Instituicao;
import com.api.teaeduc.repositories.InstituicaoFiltroRepository;
import com.api.teaeduc.utils.QueryStatement;

@Repository
public class InstituicaoFiltroReposirotyImpl extends QueryStatement<Instituicao> implements InstituicaoFiltroRepository {
    StringBuilder hql;

	@Override
	public int countFiltrado(InstituicaoFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT count(1) ");
		hql.append(" FROM Instituicao instituicao ");
		appendJOINs(filtro);
		appendWhereHQL(filtro, parameters);
		return totalResult(hql, parameters);
	}

	@Override
	public List<Instituicao> buscarPaginado(InstituicaoFiltroDTO filtro) {
		return buscarPaginado(null, filtro);
	}

	@Override
	public List<Instituicao> buscarPaginado(PageRequest pageRequest, InstituicaoFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT instituicao ");
		hql.append(" FROM Instituicao instituicao ");

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
	public StringBuilder appendJOINs(InstituicaoFiltroDTO filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder appendWhereHQL(InstituicaoFiltroDTO filtro, Map<String, Object> parameters) {
		boolean hasWhere = hql.toString().contains("WHERE");
		if (filtro.getEmail() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" instituicao.email = :email ");
			parameters.put("email", filtro.getEmail());
		}
		if (filtro.getNome() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" UPPER(instituicao.nome) LIKE :nome ");
			parameters.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
		return hql;
	}

	@Override
	public StringBuilder appendOrderByHQL(InstituicaoFiltroDTO filtro, Map<String, Object> parameters) {
		hql.append(" ORDER BY instituicao.nome ");
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
	public List<Instituicao> buscarInstituicaoByProfessor(InstituicaoFiltroDTO filtro) {
		StringBuilder sql = new StringBuilder();

		sql.append("select i.* from instituicao i ");
		sql.append("inner join professor_instituicao pi2 on pi2.ID_INSTITUICAO = i.ID_INSTITUICAO ");
		sql.append("inner join professor p on p.ID_PROFESSOR = pi2.ID_PROFESSOR and p.EMAIL = '" + filtro.getEmail() + "' ");

		Query query = entityManager.createNativeQuery(sql.toString(), Instituicao.class);

		return query.getResultList();
	}
}
