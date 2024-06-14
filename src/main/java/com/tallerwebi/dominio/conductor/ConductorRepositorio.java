package com.tallerwebi.dominio.conductor;


import com.tallerwebi.dominio.usuario.Usuario;
import com.tallerwebi.dominio.vehiculo.Vehiculo;

public interface ConductorRepositorio {
    Conductor buscarConductorPorId(Integer id);
    void editarConductor(Conductor conductor);
}
