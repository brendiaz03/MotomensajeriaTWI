package com.tallerwebi.dominio.exceptions;

public class NoSePudoGuardarElPaqueteException extends Exception {

    private final String mensajeError;

    public NoSePudoGuardarElPaqueteException(String mensajeError) {

        super(mensajeError);

        this.mensajeError = mensajeError;

    }

    @Override
    public String getMessage() {

        return mensajeError;

    }

}