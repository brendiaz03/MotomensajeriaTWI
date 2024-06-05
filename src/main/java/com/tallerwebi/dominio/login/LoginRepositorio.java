package com.tallerwebi.dominio.login;
import com.tallerwebi.dominio.usuario.Usuario;

public interface LoginRepositorio {
    Usuario buscarUsuarioPorUsernameYPassword(String username, String password);
}
