package com.api.teaeduc.services;

import java.util.List;
import java.util.Optional;

import com.api.teaeduc.dtos.AtividadeAlunoFiltroDTO;
import com.api.teaeduc.models.AtividadeAluno;

public interface AtividadeAlunoService extends BaseService<AtividadeAluno, AtividadeAlunoFiltroDTO> {
    public Optional<AtividadeAluno> findByIdAtividade(Long idAtividade, Long idAluno);
    public List<AtividadeAluno> findAtividadeAlunoByIdAluno(Long idAluno);
}
