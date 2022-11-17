package com.api.teaeduc.controllers.base;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.api.teaeduc.application.BaseApplication;
import com.api.teaeduc.dtos.BaseDTO;
import com.api.teaeduc.dtos.FiltroDTO;
import com.api.teaeduc.models.BaseEntity;
import com.api.teaeduc.utils.BusinessException;

public abstract class BaseCRUDController<D extends BaseDTO, E extends BaseEntity, F extends FiltroDTO> extends BaseController {
    @Autowired
	BaseApplication<D, E, F> baseApplication;

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable("id") Long id) {
        try {
            Optional<D> eOptional = baseApplication.buscarPorId(id);
            if (!eOptional.isPresent()) {
                return response(null);
            }
            return response(eOptional);
        } catch (Exception e) {
            return response(e);
        }
    }

    @PostMapping
    public ResponseEntity<Object> adicionar(@RequestBody D obj) {
        try {
            return response(baseApplication.salvar(obj));
        } catch (Exception e) {
            return response(e);
        }
    }

    @PutMapping
    public ResponseEntity<Object> alterar(@RequestBody D obj) {
        try {
            return response(baseApplication.alterar(obj));
        } catch (Exception e) {
            return response(e);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") Long id) {
        try {
            Optional<D> dto = baseApplication.buscarPorId(id);
            if (!dto.isPresent()) {
                return response(new BusinessException("Registro não encontrado"));
            }
            baseApplication.deletar(dto.get());
            return response(dto.get());
        } catch (Exception e) {
            return response(e);
        }
    }
}
