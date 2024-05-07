package com.tallerwebi.dominio.conductor;

import java.util.List;

public interface IServiceConductor {

//    List<Conductor> get();
//
//    List <Conductor> obtenerConductoresPorDomicilio(String domicilio); //Prueba

    Boolean verificarDatosDeRegistro(Conductor nuevoConductor) throws Exception;
}
