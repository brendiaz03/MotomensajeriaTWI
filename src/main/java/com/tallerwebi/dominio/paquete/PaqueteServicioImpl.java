package com.tallerwebi.dominio.paquete;

import com.tallerwebi.dominio.exceptions.NoSePudoGuardarElPaqueteException;
import com.tallerwebi.dominio.exceptions.PaqueteNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaqueteServicioImpl implements PaqueteServicio {

    private PaqueteRepositorio paqueteRepositorio;

    @Autowired
    public PaqueteServicioImpl(PaqueteRepositorio paqueteRepositorio) {
        this.paqueteRepositorio = paqueteRepositorio;
    }

    @Override
    public Paquete guardarPaquete(Paquete paquete) throws NoSePudoGuardarElPaqueteException {

        try {

            return this.paqueteRepositorio.guardarPaquete(paquete);

        } catch (Exception e) {

            throw new NoSePudoGuardarElPaqueteException("No se pudo guardar el paquete en nuestro sistema.");

        }

    }

    @Override
    public Paquete obtenerPaquetePorId(Integer paqueteId) throws PaqueteNoEncontradoException {

        try {

            return this.paqueteRepositorio.obtenerPaquetePorId(paqueteId);

        } catch (Exception e) {

            throw new PaqueteNoEncontradoException("No se pudo encontrar un paquete con esa ID.");

        }

    }


}
