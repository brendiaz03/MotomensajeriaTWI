package com.tallerwebi.dominio.usuario;

import com.tallerwebi.dominio.cliente.Cliente;
import com.tallerwebi.dominio.conductor.Conductor;

public interface UsuarioRepositorio {
    Usuario buscarDuplicados(String email, String nombreUsuario);

    Usuario guardarUsuario(Usuario usuario);

    void editarUsuario(Usuario usuario);

    Usuario getUsuarioById(Integer id);
}
