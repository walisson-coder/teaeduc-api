package com.api.teaeduc.services;

import com.api.teaeduc.models.Usuario;
import com.api.teaeduc.utils.BusinessException;

import com.api.teaeduc.dtos.LoginDTO;
import com.api.teaeduc.dtos.UsuarioFiltroDTO;

public interface UsuarioService extends BaseService<Usuario, UsuarioFiltroDTO> {
    public Usuario buscarUsernameAndPassword(String email, String senha);
    public Usuario buscarEmail(String email);
    public Usuario buscarUsername(Long username);
    public Usuario regraLogin(LoginDTO loginDTO) throws BusinessException;
    String buscarUsernameUsuarioLogado();
}
