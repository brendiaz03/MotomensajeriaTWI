package com.tallerwebi.dominio.conductor;


public interface IRepositoryConductor {
    void registrar(Conductor nuevoConductor);

    Conductor buscarConductor(Integer id);
    void actualizarConductor(Conductor nuevoConductor);
    Conductor buscarDuplicados(String email, String nombreUsuario);
}
