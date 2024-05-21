package com.tallerwebi.dominio.ayuda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceAyudaImpl implements IServiceAyuda {

    private IRepositorioAyuda repositorioAyuda;

    @Autowired
    public ServiceAyudaImpl(IRepositorioAyuda repositorioAyuda) {
        this.repositorioAyuda = repositorioAyuda;
    }


}
