package com.api.teaeduc.services.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.api.teaeduc.dtos.UsuarioFiltroDTO;
import com.api.teaeduc.interceptors.bean.UsuarioLogadoBean;
import com.api.teaeduc.dtos.LoginDTO;
import com.api.teaeduc.models.Usuario;
import com.api.teaeduc.repositories.UsuarioFiltroRepository;
import com.api.teaeduc.repositories.UsuarioRepository;
import com.api.teaeduc.services.UsuarioService;
import com.api.teaeduc.utils.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, UsuarioFiltroDTO> implements UsuarioService {

    @Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	UsuarioFiltroRepository usuarioFiltroRepository;

	@Resource(name = "requestScopeUsuarioLogado")
	private UsuarioLogadoBean requestScopeUsuarioLogado;


    @Override
    public List<Usuario> buscarFiltrado(UsuarioFiltroDTO filtro) {
        return this.usuarioFiltroRepository.buscarPaginado(filtro);
    }

    @Override
    public List<Usuario> buscarFiltrado(PageRequest pageRequest, UsuarioFiltroDTO filtro) {
        return this.usuarioFiltroRepository.buscarPaginado(pageRequest, filtro);
    }

    @Override
    public int countFiltrado(UsuarioFiltroDTO filtro) {
        return this.usuarioFiltroRepository.countFiltrado(filtro);
    }

    @Override
	public Usuario buscarEmail(String email) {
		UsuarioFiltroDTO filtro = new UsuarioFiltroDTO();
		filtro.setEmail(email);
		return usuarioFiltroRepository.buscarPaginado(filtro).get(0);
	}

	@Override
	public String buscarUsernameUsuarioLogado() {
		if (requestScopeUsuarioLogado != null && requestScopeUsuarioLogado.getUsername() != null) {
			return requestScopeUsuarioLogado.getUsername();
		}
		return null;
	}



    @Override
	public Usuario buscarUsernameAndPassword(String email, String senha) {
		UsuarioFiltroDTO filtro = new UsuarioFiltroDTO();
		filtro.setEmail(email);
		List<Usuario> usuarios = buscarFiltrado(filtro);
		Optional<Usuario> usuarioOptional = usuarios.stream().findFirst();
		if (usuarioOptional.isPresent()) {
			return usuarioOptional.get();
		}
		return null;
	}

	@Override
	public Usuario buscarUsername(Long login) {
		UsuarioFiltroDTO filtro = new UsuarioFiltroDTO();
		filtro.setCpfCnpj(login);
		List<Usuario> usuarios = buscarFiltrado(filtro);
		Optional<Usuario> usuarioOptional = usuarios.stream().findFirst();
		if (usuarioOptional.isPresent()) {
			return usuarioOptional.get();
		}
		return null;
	}

	@Override
	public Usuario regraLogin(LoginDTO loginDTO) throws BusinessException {
		List<Usuario> usuarios = this.usuarioFiltroRepository.buscarLogin(loginDTO);
		if (usuarios == null || usuarios.isEmpty()) {
			throw new BusinessException("Usuário ou senha podem estar incorretos");
		}

		if (usuarios.size() > 1) {
			throw new BusinessException("Multiplos usuário com o mesmo login foram encontrados.");
		}

		Usuario vo = usuarios.get(0);

		try {
			if (!vo.getSenha().equals(loginDTO.getSenha())) {
				throw new BusinessException("Senha incorreta.");
			}
		} catch (Exception e) {
			
		}

		return vo;
	}

    
    
}
