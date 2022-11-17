package com.api.teaeduc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.teaeduc.application.ExercicioApplication;
import com.api.teaeduc.controllers.base.BaseController;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("${privateApi}/exercicio")
public class ExercicioController extends BaseController {
    @Autowired
	ExercicioApplication exercicioApplication;

    @PostMapping(value="/fragmentar")
    public ResponseEntity<Object> gerarExercicio(@RequestParam(value = "palavra") String palavra){
        try {
            return response(this.exercicioApplication.separarPorSilaba(palavra));
        } catch (Exception e) {
            return response(e);
        }
    }
}
