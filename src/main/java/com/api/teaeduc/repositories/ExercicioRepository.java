package com.api.teaeduc.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.api.teaeduc.models.Exercicio;

@Repository
public interface ExercicioRepository extends BaseRepository<Exercicio> {
    List<Exercicio> findByIdAtividade(Long idAtividade);
}
