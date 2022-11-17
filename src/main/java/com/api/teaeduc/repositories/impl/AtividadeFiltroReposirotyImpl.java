package com.api.teaeduc.repositories.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.api.teaeduc.dtos.AtividadeFiltroDTO;
import com.api.teaeduc.models.Atividade;
import com.api.teaeduc.models.enumeration.TipoAtividadeEnum;
import com.api.teaeduc.repositories.AtividadeFiltroRepository;
import com.api.teaeduc.utils.QueryStatement;

@Repository
public class AtividadeFiltroReposirotyImpl extends QueryStatement<Atividade> implements AtividadeFiltroRepository {
    StringBuilder hql;

	@Override
	public int countFiltrado(AtividadeFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT count(1) ");
		hql.append(" FROM Atividade atividade ");
		appendJOINs(filtro);
		appendWhereHQL(filtro, parameters);
		return totalResult(hql, parameters);
	}

	@Override
	public List<Atividade> buscarPaginado(AtividadeFiltroDTO filtro) {
		return buscarPaginado(null, filtro);
	}

	@Override
	public List<Atividade> buscarPaginado(PageRequest pageRequest, AtividadeFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT atividade ");
		hql.append(" FROM Atividade atividade ");

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
	public StringBuilder appendWhereHQL(AtividadeFiltroDTO filtro, Map<String, Object> parameters) {
		boolean hasWhere = hql.toString().contains("WHERE");

		if (filtro.getId() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" atividade.id = :id ");
			parameters.put("id", filtro.getId());
		}

		if (filtro.getIdProfessor() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" atividade.idProfessor = :idProfessor ");
			parameters.put("idProfessor", filtro.getIdProfessor());
		}

		if (filtro.getNomeAtividade() != null && !filtro.getNomeAtividade().equals("")) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" UPPER(atividade.nomeAtividade) LIKE :nome ");
			parameters.put("nome", "%" + filtro.getNomeAtividade().toUpperCase() + "%");
		}
		
		List<TipoAtividadeEnum> tipoAtividade = new ArrayList<>();

		if (filtro.getTipoImagem() != null && filtro.getTipoImagem().equals(Boolean.TRUE)) {
			tipoAtividade.add(TipoAtividadeEnum.IMAGENS);
		}
		if (filtro.getTipoLetra() != null && filtro.getTipoLetra().equals(Boolean.TRUE)) {
			tipoAtividade.add(TipoAtividadeEnum.LETRAS);
		}
		if (filtro.getTipoSilaba() != null && filtro.getTipoSilaba().equals(Boolean.TRUE)) {
			tipoAtividade.add(TipoAtividadeEnum.SILABAS);
		}

		if (tipoAtividade != null && !tipoAtividade.isEmpty()) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" atividade.tipoAtividade IN (:tipoSilaba) ");
			parameters.put("tipoSilaba", tipoAtividade);
		}

		return hql;
	}

	@Override
	public StringBuilder appendOrderByHQL(AtividadeFiltroDTO filtro, Map<String, Object> parameters) {
		hql.append(" ORDER BY atividade.nomeAtividade ");
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
	public StringBuilder appendJOINs(AtividadeFiltroDTO filtro) {
		// TODO Auto-generated method stub
		return null;
	}
}
