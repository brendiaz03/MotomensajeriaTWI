package com.tallerwebi.dominio.conductor;


public interface IRepositorioConductor {
    //la interfaz que se inyecta en repositorio define los metodos que se van a implementar
    Conductor buscarConductorPorNroDni(Integer nroDni);
    void ingresarConductor(Conductor conductor);
}

