package com.api.teaeduc.services;

import com.api.teaeduc.models.Instituicao;

import java.util.List;

import com.api.teaeduc.dtos.InstituicaoFiltroDTO;

public interface InstituicaoService extends BaseService<Instituicao, InstituicaoFiltroDTO> {
    List<Instituicao> buscarInstituicaoByProfessor(InstituicaoFiltroDTO filtro);
}
