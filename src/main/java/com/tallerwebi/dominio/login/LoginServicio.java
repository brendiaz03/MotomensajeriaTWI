package com.tallerwebi.dominio.login;

import com.tallerwebi.dominio.conductor.Conductor;
import com.tallerwebi.dominio.usuario.Usuario;

public interface LoginServicio {

    Usuario consultarUsuario(String user, String pass);
}
