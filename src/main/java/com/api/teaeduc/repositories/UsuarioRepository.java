package com.api.teaeduc.repositories;

import com.api.teaeduc.models.Usuario;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario> {
    List<Usuario> findByEmail(String email);
}
