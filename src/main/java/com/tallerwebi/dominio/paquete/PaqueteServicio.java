package com.tallerwebi.dominio.paquete;

import com.tallerwebi.dominio.exceptions.NoSePudoGuardarElPaqueteException;
import com.tallerwebi.dominio.exceptions.PaqueteNoEncontradoException;

public interface PaqueteServicio {

    Paquete guardarPaquete(Paquete paquete) throws NoSePudoGuardarElPaqueteException;

    Paquete obtenerPaquetePorId(Integer paqueteId) throws PaqueteNoEncontradoException;

}
