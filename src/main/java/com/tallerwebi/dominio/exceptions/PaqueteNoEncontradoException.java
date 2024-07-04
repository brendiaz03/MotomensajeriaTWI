package com.tallerwebi.dominio.exceptions;

public class PaqueteNoEncontradoException extends Exception {

    private final String mensajeError;

    public PaqueteNoEncontradoException(String mensajeError) {

        super(mensajeError);

        this.mensajeError = mensajeError;

    }

    @Override
    public String getMessage() {

        return mensajeError;

    }

}

