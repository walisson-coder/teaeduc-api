package com.api.teaeduc.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.teaeduc.dtos.LoginDTO;
import com.api.teaeduc.dtos.UsuarioDTO;
import com.api.teaeduc.dtos.UsuarioFiltroDTO;
import com.api.teaeduc.models.Usuario;
import com.api.teaeduc.services.UsuarioService;
import com.api.teaeduc.utils.BusinessException;

@Service
public class UsuarioApplication extends BaseApplication<UsuarioDTO, Usuario, UsuarioFiltroDTO> {
    @Autowired
	UsuarioService usuarioService;


    @Override
	public UsuarioDTO mapToDTO(Usuario e) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(e.getId());
		dto.setEmail(e.getEmail());
		dto.setSenha(e.getSenha());
        dto.setDataAcesso(e.getDataAcesso());
		dto.setTipo(e.getTipo());
        dto.setAtivo(e.getAtivo());
		return dto;
	}

	@Override
	public Usuario mapToEntity(UsuarioDTO dto) {
		Usuario e = new Usuario();
		e.setId(dto.getId());
		e.setEmail(dto.getEmail());
		e.setSenha(dto.getSenha());
        e.setDataAcesso(dto.getDataAcesso());
		e.setTipo(dto.getTipo());
        e.setAtivo(dto.getAtivo());
		return e;
	}

	public List<UsuarioDTO> buscarFiltrado(UsuarioFiltroDTO filtro) {
		List<Usuario> usuarios = this.usuarioService.buscarFiltrado(filtro);
		return usuarios.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

    public UsuarioDTO buscarUser(Long login) {
		Usuario usuario = this.usuarioService.buscarUsername(login);
		if (usuario == null) {
			return null;
		}
		return this.mapToDTO(usuario);
	}

	public String buscarUsernameUsuarioLogado() {
		return usuarioService.buscarUsernameUsuarioLogado();
	}

	public UsuarioDTO buscarUserPorEmail(String email) {
		Usuario usuario = this.usuarioService.buscarEmail(email);
		if (usuario == null) {
			return null;
		}
		return this.mapToDTO(usuario);
	}

	public UsuarioDTO regraLogin(LoginDTO loginDTO) throws BusinessException {
		Usuario vo = this.usuarioService.regraLogin(loginDTO);
		return mapToDTO(vo);
	}

	@Override
    @Transactional(rollbackOn = Exception.class)
	public UsuarioDTO alterar(UsuarioDTO dto) throws Exception {
        Usuario e = this.mapToEntity(dto);

        UsuarioDTO dtoSaved = this.mapToDTO(this.usuarioService.alterar(e));

		return dtoSaved;
	}

    
}
