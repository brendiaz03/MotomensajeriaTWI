package com.tallerwebi.dominio.paquete;

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
    public Paquete guardarPaquete(Paquete paquete) {
        return this.paqueteRepositorio.guardarPaquete(paquete);
    }

    @Override
    public void editarPaquete(Paquete paquete) {
        this.paqueteRepositorio.editarPaquete(paquete);
    }

    @Override
    public Paquete obtenerPaquetePorId(Integer paqueteId) {
        return this.paqueteRepositorio.obtenerPaquetePorId(paqueteId);
    }


}
