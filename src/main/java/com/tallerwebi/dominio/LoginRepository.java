package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.usuarios.Cliente;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;

public interface LoginRepository {

    void guardarConductor(Conductor conductor);

    Cliente validarCliente(String email, String password);

    Conductor validarConductor(String email, String password);

    void guardarCliente(Cliente cliente);
}