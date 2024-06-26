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
    public Paquete guardarPaquete(Paquete paquete) throws PaqueteNoEncontradoException {

        if(paquete==null){
            throw new PaqueteNoEncontradoException();  //NOSE SI SE NECESITA ESTA VERIFICACION, LA DEJO POR LAS DUDAS
        }
        return this.paqueteRepositorio.guardarPaquete(paquete);
    }

    @Override
    public Paquete obtenerPaquetePorId(Integer paqueteId) throws PaqueteNoEncontradoException{

        try {
            return this.paqueteRepositorio.obtenerPaquetePorId(paqueteId);
        } catch (PaqueteNoEncontradoException e) {
            throw new PaqueteNoEncontradoException();
        }

    }


}
