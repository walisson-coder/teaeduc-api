package com.api.teaeduc.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.api.teaeduc.models.ImagensExercicios;

@Repository
public interface ImagensExerciciosRepository extends BaseRepository<ImagensExercicios> {
    List<ImagensExercicios> findByIdAluno(Long idAluno);
}
