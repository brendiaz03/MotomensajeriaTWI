package com.tallerwebi.dominio.usuario;

import org.springframework.transaction.annotation.Transactional;

public interface UsuarioRepositorio {
    Usuario buscarDuplicados(String email, String nombreUsuario);

    Usuario guardarUsuario(Usuario usuario);

    void editarUsuario(Usuario usuario);

    Usuario getUsuarioById(Integer id);

}
