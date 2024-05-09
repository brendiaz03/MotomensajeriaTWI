package com.tallerwebi.dominio.login;

import com.tallerwebi.dominio.conductor.Conductor;

public interface ILoginRepository {
    Conductor buscarConductorPorUsernameYPassword(String username, String password);
}
