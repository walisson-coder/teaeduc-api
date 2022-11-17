package com.api.teaeduc.services.impl;

import java.util.List;
import java.util.Optional;

import com.api.teaeduc.dtos.FiltroDTO;
import com.api.teaeduc.models.BaseEntity;
import com.api.teaeduc.repositories.BaseRepository;
import com.api.teaeduc.services.BaseService;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseServiceImpl<T extends BaseEntity, F extends FiltroDTO> implements BaseService<T, F> {

    @Autowired
	BaseRepository<T> baseRepository;
	
    @Override
    public Optional<T> buscarPorId(Long id) {
        return baseRepository.findById(id);
    }

    @Override
    public T salvar(T obj, String username) throws Exception {
        return baseRepository.save(obj);
    }

    @Override
    public T salvar(T obj) throws Exception {
        return baseRepository.save(obj);
	}
	
	@Override
    public List<T> salvarLista(List<T> list, String username) throws Exception {
        return this.baseRepository.saveAll(list);
    }

    @Override
    public List<T> salvarLista(List<T> list) throws Exception {
        return this.baseRepository.saveAll(list);
	}

    @Override
    public void removerLista(List<T> list) throws Exception {
        this.baseRepository.deleteAll(list);
	}
	
	@Override
    public void deletar(T obj, String username) throws Exception {
        baseRepository.delete(obj);
    }

    @Override
    public void deletar(T obj) throws Exception {
        baseRepository.delete(obj);
    }

    @Override
    public List<T> buscarTodos() {
        return baseRepository.findAll();
	}
	
	@Override
    public T alterar(T obj, String username) throws Exception {
		return baseRepository.save(obj);
    }

    @Override
    public T alterar(T obj) throws Exception {
		return baseRepository.save(obj);
    }
}
