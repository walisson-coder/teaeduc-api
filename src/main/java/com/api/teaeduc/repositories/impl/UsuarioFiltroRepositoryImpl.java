package com.api.teaeduc.repositories.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.api.teaeduc.dtos.LoginDTO;
import com.api.teaeduc.dtos.UsuarioFiltroDTO;
import com.api.teaeduc.models.Usuario;
import com.api.teaeduc.repositories.UsuarioFiltroRepository;
import com.api.teaeduc.utils.QueryStatement;

@Repository
public class UsuarioFiltroRepositoryImpl extends QueryStatement<Usuario> implements UsuarioFiltroRepository {
    StringBuilder hql;

	@Override
	public int countFiltrado(UsuarioFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT count(1) ");
		hql.append(" FROM Usuario usuario ");
		appendJOINs(filtro);
		appendWhereHQL(filtro, parameters);
		return totalResult(hql, parameters);
	}

	@Override
	public List<Usuario> buscarPaginado(UsuarioFiltroDTO filtro) {
		return buscarPaginado(null, filtro);
	}

	@Override
	public List<Usuario> buscarLogin(LoginDTO loginDTO){
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT usuario ");
		hql.append(" FROM Usuario usuario ");

		boolean hasWhere = hql.toString().contains("WHERE");
		hasWhere = appendWhereOrAnd(hql, hasWhere);

		hql.append(" usuario.email = :login ");
		parameters.put("login", loginDTO.getLogin());

		hasWhere = appendWhereOrAnd(hql, hasWhere);
		hql.append(" usuario.senha = :senha ");
		parameters.put("senha", loginDTO.getSenha());


		hasWhere = appendWhereOrAnd(hql, hasWhere);
		hql.append(" usuario.tipo = :tipo ");
		parameters.put("tipo", loginDTO.getTipo());


		return asList(hql, parameters, null, null);
		
	}

	@Override
	public List<Usuario> buscarPaginado(PageRequest pageRequest, UsuarioFiltroDTO filtro) {
		hql = new StringBuilder();
		Map<String, Object> parameters = new HashMap<>();
		hql.append(" SELECT usuario ");
		hql.append(" FROM Usuario usuario ");

		//appendJOINs(filtro);
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
	public StringBuilder appendJOINs(UsuarioFiltroDTO filtro) {
		return hql;
	}

	@Override
	public StringBuilder appendWhereHQL(UsuarioFiltroDTO filtro, Map<String, Object> parameters) {
		boolean hasWhere = hql.toString().contains("WHERE");
		if (filtro.getEmail() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" usuario.email = :email ");
			parameters.put("email", filtro.getEmail());
		}

		if (filtro.getCpfCnpj() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" usuario.cpfCnpj = :login ");
			parameters.put("login", filtro.getCpfCnpj().toString());
		}

		if (filtro.getTipo() != null) {
			hasWhere = appendWhereOrAnd(hql, hasWhere);
			hql.append(" usuario.tipo = :tipo ");
			parameters.put("tipo", filtro.getTipo());
		}
		
		return hql;
	}

	@Override
	public StringBuilder appendOrderByHQL(UsuarioFiltroDTO filtro, Map<String, Object> parameters) {
		hql.append(" ORDER BY usuario.email ");
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
