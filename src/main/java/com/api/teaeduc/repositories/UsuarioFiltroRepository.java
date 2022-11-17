package com.api.teaeduc.repositories;

import java.util.List;

import com.api.teaeduc.dtos.LoginDTO;
import com.api.teaeduc.dtos.UsuarioFiltroDTO;
import com.api.teaeduc.models.Usuario;

public interface UsuarioFiltroRepository extends BaseFiltroRepository<Usuario, UsuarioFiltroDTO> {
    public List<Usuario> buscarLogin(LoginDTO loginDTO);
}
