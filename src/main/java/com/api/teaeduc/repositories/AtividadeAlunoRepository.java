package com.api.teaeduc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.api.teaeduc.models.AtividadeAluno;

@Repository
public interface AtividadeAlunoRepository extends BaseRepository<AtividadeAluno> {
    Optional<AtividadeAluno> findByIdAtividadeAndIdAluno(Long idAtividade, Long idAluno);
    List<AtividadeAluno> findByIdAluno(Long idAluno);
}
