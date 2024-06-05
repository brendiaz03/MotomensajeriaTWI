package com.tallerwebi.dominio.paquete;

public class PaqueteNoEncontradoException extends Exception {

    public PaqueteNoEncontradoException() {
        super("No se encontró el paquete buscado.");
    }
}
