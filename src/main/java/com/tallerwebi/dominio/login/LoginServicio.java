package com.tallerwebi.dominio.login;

import com.tallerwebi.dominio.conductor.Conductor;

public interface LoginServicio {

    Conductor consultarUsuario(String user, String pass);
}