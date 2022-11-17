package com.api.teaeduc.repositories;

import java.util.List;
import java.util.Map;

import com.api.teaeduc.dtos.FiltroDTO;
import com.api.teaeduc.models.BaseEntity;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseFiltroRepository<E extends BaseEntity, F extends FiltroDTO> {
	
	public int countFiltrado(F filtro);

	public List<E> buscarPaginado(F filtro);

	public List<E> buscarPaginado(PageRequest pageRequest, F filtro);

	public StringBuilder appendJOINs(F filtro);

	public StringBuilder appendWhereHQL(F filtro, Map<String, Object> parameters);

	public StringBuilder appendOrderByHQL(F filtro, Map<String, Object> parameters);
}
