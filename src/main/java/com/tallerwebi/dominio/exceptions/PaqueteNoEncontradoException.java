package com.tallerwebi.dominio.exceptions;

public class PaqueteNoEncontradoException extends Exception {

    public PaqueteNoEncontradoException() {
        super("No se encontró el paquete buscado.");
    }
}
