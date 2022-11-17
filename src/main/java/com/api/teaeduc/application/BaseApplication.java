package com.api.teaeduc.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.api.teaeduc.dtos.BaseDTO;
import com.api.teaeduc.dtos.FiltroDTO;
import com.api.teaeduc.dtos.paginacao.ListWrapper;
import com.api.teaeduc.models.BaseEntity;
import com.api.teaeduc.services.BaseService;

public abstract class BaseApplication<D extends BaseDTO, E extends BaseEntity, F extends FiltroDTO> {

    public abstract E mapToEntity(D dto);
    public abstract D mapToDTO(E e);

    @Autowired
    BaseService<E, F> baseService;

    public ListWrapper<D> buscarPaginado(int page, int size, F filtro) {
        Sort.Direction sDirection = null;
        String[] properties = new String[0];
        return buscarPaginado(page, size, sDirection, filtro, properties);
    }

    public ListWrapper<D> buscarPaginado(int page, int size, Sort.Direction sortDirection, F filtro, String... properties) {
        if (sortDirection == null) {
            sortDirection = Direction.ASC;
        }

        if (properties == null || properties.length == 0) {
            properties = new String[1];
            properties[0] = "id";
        }

        PageRequest pageRequest = PageRequest.of(
            page,
            size,
            sortDirection,
            properties);
		List<E> entities = this.baseService.buscarFiltrado(pageRequest, filtro);
		ListWrapper<D> listWrapper = new ListWrapper<>();

		listWrapper.setPage(pageRequest.getPageNumber());
		int totalResults = this.baseService.countFiltrado(filtro);
		listWrapper.setPages((int) Math.ceil(Double.valueOf(Double.valueOf(totalResults) / Double.valueOf(pageRequest.getPageSize()))));
		listWrapper.setTotalResults(totalResults);
		listWrapper.setList(entities.stream().map(this::mapToDTO).collect(Collectors.toList()));
		
		return listWrapper;
    }

    public Optional<D> buscarPorId(Long id) {
        Optional<E> e = baseService.buscarPorId(id);
        if(e.isPresent())
            return Optional.of(this.mapToDTO(e.get()));
        return Optional.empty();
	}
	
	public D salvar(D dto, String username) throws Exception {
        E e = this.mapToEntity(dto);
        return this.mapToDTO(baseService.salvar(e, username));
    }

    public D salvar(D dto) throws Exception {
        E e = this.mapToEntity(dto);
        return this.mapToDTO(baseService.salvar(e));
	}
	
    public D alterar(D dto, String username) throws Exception{
        E e = this.mapToEntity(dto);
        return this.mapToDTO(baseService.alterar(e, username));
    }

    public D alterar(D dto) throws Exception{
        E e = this.mapToEntity(dto);
        return this.mapToDTO(baseService.alterar(e));
    }

    public void deletar(D dto, String username) throws Exception {
        E e = this.mapToEntity(dto);
        baseService.deletar(e, username);
    }

    public void deletar(D dto) throws Exception {
        E e = this.mapToEntity(dto);
        baseService.deletar(e);
    }

    public List<D> buscarTodos() {
        return baseService.buscarTodos().stream().map(value->this.mapToDTO(value)).collect(Collectors.toList());
    }

    
    
}
