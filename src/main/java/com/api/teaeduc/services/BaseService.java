package com.api.teaeduc.services;


import java.util.List;
import java.util.Optional;

import com.api.teaeduc.dtos.FiltroDTO;
import com.api.teaeduc.models.BaseEntity;

import org.springframework.data.domain.PageRequest;


public interface BaseService<T extends BaseEntity, F extends FiltroDTO> {
	
	Optional<T> buscarPorId(Long id);
	T salvar(T obj, String username) throws Exception;
	T salvar(T obj) throws Exception;
	List<T> salvarLista(List<T> list, String username) throws Exception;
    List<T> salvarLista(List<T> list) throws Exception;
	void deletar(T obj, String username) throws Exception;
	void deletar(T obj) throws Exception;
	List<T> buscarTodos();
	T alterar(T e, String username) throws Exception;
	T alterar(T e) throws Exception;
	List<T> buscarFiltrado(F filtro);
	List<T> buscarFiltrado(PageRequest pageRequest, F filtro);
	int countFiltrado(F filtro);
	void removerLista(List<T> list) throws Exception;
}
