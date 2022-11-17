package com.api.teaeduc.repositories;

import java.util.List;

import com.api.teaeduc.dtos.InstituicaoFiltroDTO;
import com.api.teaeduc.models.Instituicao;


public interface InstituicaoFiltroRepository extends BaseFiltroRepository<Instituicao, InstituicaoFiltroDTO> {
    List<Instituicao> buscarInstituicaoByProfessor(InstituicaoFiltroDTO filtro);
    
}
