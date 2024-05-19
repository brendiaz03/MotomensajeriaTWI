package com.tallerwebi.dominio.conductor;


public interface IRepositoryConductor {
    void registrar(Conductor nuevoConductor);

    Conductor buscarConductor(Integer id);
    Boolean editarConductor(Conductor nuevoConductor);
    Conductor buscarDuplicados(String email, String nombreUsuario);
    void borrarConductor(Conductor conductor);
}
