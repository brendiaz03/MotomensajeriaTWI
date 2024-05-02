package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.usuarios.Cliente;
import com.tallerwebi.dominio.entidades.usuarios.Conductor;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;

public interface LoginService {

    void guardarConductor(Conductor conductor) throws UsuarioExistente;

    void guardarCliente(Cliente cliente) throws UsuarioExistente;

    Conductor validarConductor(String email, String password);

    Cliente validarCliente(String email, String password);
}