package com.tallerwebi.dominio.conductor;

public interface IServiceConductor {

//    List<Conductor> get();
//
//    List <Conductor> obtenerConductoresPorDomicilio(String domicilio); //Prueba

    String verificarDatosDeRegistro(Conductor nuevoConductor) throws Exception;
}
